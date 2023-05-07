package com.samuelfernando.sysmapparrot.modules.comment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.samuelfernando.sysmapparrot.config.exception.BusinessRuleException;
import com.samuelfernando.sysmapparrot.config.exception.ForbiddenException;
import com.samuelfernando.sysmapparrot.config.exception.NotFoundException;
import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentRequest;
import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.dto.UserCommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.entity.Comment;
import com.samuelfernando.sysmapparrot.modules.comment.model.UserComment;
import com.samuelfernando.sysmapparrot.modules.comment.repository.CommentRepository;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@Service
public class CommentService implements ICommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private IUserService userService;

	@Override
	public CommentResponse getComments(UUID postId) {
		Comment comment = findByPostId(postId);
		return new CommentResponse(comment.getPostId(), comment.getUserComments());
	}

	@Override
	public void createPostCommentSection(UUID postId) {
		List<UserComment> emptyUserComments = new ArrayList<UserComment>();
		commentRepository.save(new Comment(postId, emptyUserComments));
	}

	@Override
	public void insertPostComment(UUID id, CommentRequest commentRequest) {
		Comment comment = findByPostId(id);
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));

		UserProfileResponse profileResponse = userService.findUserProfileById(userId);
		Set<UUID> likes = new HashSet<UUID>();
		LocalDateTime createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now();

		UserProfile userProfile = new UserProfile(profileResponse.name, profileResponse.photoUri,
				profileResponse.following, profileResponse.followers, profileResponse.createdAt,
				profileResponse.updatedAt);

		UserComment userComment = new UserComment(commentRequest.description, userId, userProfile, likes, createdAt,
				updatedAt);
		comment.getUserComments().add(userComment);

		commentRepository.save(comment);
	}

	@Override
	public void likePostComment(UUID postId, UUID commentId) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		Comment comment = findByPostId(postId);
		Optional<UserComment> userCommentOptional = comment.getUserComments().stream()
				.filter(userComment -> userComment.getId().equals(commentId)).findFirst();
		
		if (userCommentOptional.isEmpty()) {
			throw new NotFoundException("Comment with ID: " + comment + " not found");
		}
		
		UserComment userComment = userCommentOptional.get();
		int commentIndex = comment.getUserComments().indexOf(userComment);
		
		if (userComment.getLikes().contains(userId)) {
			throw new BusinessRuleException("You already liked this comment");
		} else {
			userComment.getLikes().add(userId);
			comment.getUserComments().set(commentIndex, userComment);
			commentRepository.save(comment);
		}
	}
	
	@Override
	public void unlikePostComment(UUID postId, UUID commentId) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		
		Comment comment = findByPostId(postId);
		Optional<UserComment> userCommentOptional = comment.getUserComments().stream()
				.filter(userComment -> userComment.getId().equals(commentId)).findFirst();
		
		if (userCommentOptional.isEmpty()) {
			throw new NotFoundException("Comment with ID: " + comment + " not found");
		}
		
		UserComment userComment = userCommentOptional.get();
		int commentIndex = comment.getUserComments().indexOf(userComment);
		
		if (!userComment.getLikes().contains(userId)) {
			throw new BusinessRuleException("You don't have liked this comment");
		} else {
			userComment.getLikes().remove(userId);
			comment.getUserComments().set(commentIndex, userComment);
			commentRepository.save(comment);
		}
	}
	
	@Override
	public UserCommentResponse getComment(UUID postId, UUID commentId) {
		return UserCommentResponse.of(postId, findUserCommentInList(postId, commentId));
	}

	@Override
	public void updateUserComment(UUID postId, UUID userCommentId, CommentRequest commentRequest) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		
		Comment comment = findByPostId(postId);
		Optional<UserComment> userCommentOptional = comment.getUserComments().stream()
				.filter(userComment -> userComment.getId().equals(userCommentId)).findFirst();
		
		if (userCommentOptional.isEmpty()) {
			throw new NotFoundException("Comment with ID: " + userCommentId + " not found");
		}
		
		UserComment userComment = userCommentOptional.get();
		int commentIndex = comment.getUserComments().indexOf(userComment);
		
		verifyUserPermissionToModifyData(userId, userComment);
		
		userComment.setDescription(commentRequest.description);
		userComment.setUpdatedAt(LocalDateTime.now());
		
		comment.getUserComments().set(commentIndex, userComment);
		
		commentRepository.save(comment);
	}

	@Override
	public void deleteUserComment(UUID postId, UUID userCommentId) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		Comment comment = findByPostId(postId);
		Optional<UserComment> userCommentOptional = comment.getUserComments().stream()
				.filter(userComment -> userComment.getId().equals(userCommentId)).findFirst();
		
		if (userCommentOptional.isEmpty()) {
			throw new NotFoundException("Comment with ID: " + userCommentId + " not found");
		}
		
		UserComment userComment = userCommentOptional.get();
		
		verifyUserPermissionToModifyData(userId, userComment);
		
		comment.getUserComments().remove(userComment);
		commentRepository.save(comment);
	}

	@Override
	public void deletePostCommentSection(UUID id) {
		Comment comment = findByPostId(id);
		commentRepository.delete(comment);
	}

	private Comment findByPostId(UUID postId) {
		Optional<Comment> comment = commentRepository.findByPostId(postId);

		if (!comment.isPresent()) {
			throw new NotFoundException("Comment section for Post ID: " + postId + " not found");
		}

		return comment.get();
	}
	
	private UserComment findUserCommentInList(UUID postId, UUID commentId) {
		Optional<UserComment> userComment = findByPostId(postId).getUserComments().stream()
				.filter(comment -> comment.getId().equals(commentId)).findFirst();
		
		if (!userComment.isPresent()) {
			throw new NotFoundException("Comment with ID: " + commentId + " not found");
		}
		
		return userComment.get();
	}
	
	private void verifyUserPermissionToModifyData(UUID userId, UserComment userComment) {
		if (!userId.equals(userComment.getUserId())) {
			throw new ForbiddenException("You don't have permission to change the comment content");
		}
	}
}
