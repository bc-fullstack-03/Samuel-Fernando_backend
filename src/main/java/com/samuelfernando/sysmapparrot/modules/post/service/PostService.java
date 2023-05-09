package com.samuelfernando.sysmapparrot.modules.post.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.internetmonitor.model.InternalServerErrorException;
import com.samuelfernando.sysmapparrot.config.exception.BusinessRuleException;
import com.samuelfernando.sysmapparrot.config.exception.ForbiddenException;
import com.samuelfernando.sysmapparrot.config.exception.NotFoundException;
import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.service.ICommentService;
import com.samuelfernando.sysmapparrot.modules.file.service.IFileUploadService;
import com.samuelfernando.sysmapparrot.modules.post.dto.CreatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponse;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponsePage;
import com.samuelfernando.sysmapparrot.modules.post.dto.UpdatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.entity.Post;
import com.samuelfernando.sysmapparrot.modules.post.repository.PostRepository;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;
import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@Service
public class PostService implements IPostService {
	@Autowired
	private IUserService userService;
	@Autowired
	private IFileUploadService fileUploadService;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ICommentService commentService;

	@Value("${app-config.secrets.aws-s3-url}")
	private String s3Url;
	@Value("${app-config.api-env}")
	private String apiEnv;

	@Override
	public List<PostResponse> getMyPosts() {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		return postRepository.findAllByUserId(userId).stream().map(post -> {
			post.setDescription(generateDescription(post));
			CommentResponse commentResponse = commentService.getComments(post.getId());
			return PostResponse.of(post, commentResponse);
		}).collect(Collectors.toList());
	}

	@Override
	public List<PostResponse> getAllPostsByUserId(UUID userId) {
		return postRepository.findAllByUserId(userId).stream().map(post -> {
			post.setDescription(generateDescription(post));
			CommentResponse commentResponse = commentService.getComments(post.getId());
			return PostResponse.of(post, commentResponse);
		}).collect(Collectors.toList());
	}

	@Override
	public PostResponse getPostById(UUID id) {
		Post post = findById(id);
		post.setDescription(generateDescription(post));
		CommentResponse commentResponse = commentService.getComments(id);
		return PostResponse.of(post, commentResponse);
	}

	@Override
	public PostResponsePage getFeed(UserProfileResponse userProfileResponse, int page) {
		Set<UUID> profileIds = new HashSet<>();

		profileIds.add(userProfileResponse.idUser);
		profileIds.addAll(userProfileResponse.following);

		Page<Post> postsPage = postRepository.findAllPostsByUserIdIn(profileIds,
				PageRequest.of(page, 10, Sort.by("createdAt").descending()));

		List<PostResponse> postResponseList = postsPage.stream().map(post -> {
			post.setDescription(generateDescription(post));
			CommentResponse commentResponse = commentService.getComments(post.getId());
			return PostResponse.of(post, commentResponse);
		}).collect(Collectors.toList());

		return PostResponsePage.of(postsPage, postResponseList);
	}

	@Override
	@Transactional
	public void createPost(CreatePostRequest post, MultipartFile photo) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		UserProfileResponse userProfileResponse = userService.findUserProfileById(userId);

		if (isEmpty(userProfileResponse) || userId == null) {
			throw new NotFoundException("User ID: " + userId + " not found");
		}

		UserProfile userProfile = new UserProfile(userProfileResponse.name, userProfileResponse.photoUri,
				userProfileResponse.following, userProfileResponse.followers, userProfileResponse.createdAt,
				userProfileResponse.updatedAt);

		LocalDateTime createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now();
		Set<UUID> likes = new HashSet<UUID>();
		Post newPost = new Post(post.title, post.description, userId, userProfile, likes, post.isImage, createdAt,
				updatedAt);

		if (post.isImage == true) {
			newPost.setDescription("");
		}
		
		if (!isEmpty(photo) && post.isImage == true) {
			String filename = newPost.getId() + "."
					+ photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
			try {
				newPost.setDescription(fileUploadService.upload(photo, filename));
			} catch (Exception e) {
				throw new InternalServerErrorException("An error ocurred when trying to save the photo");
			}
		}
	
		postRepository.save(newPost);
		commentService.createPostCommentSection(newPost.getId());
	}

	public void likePost(UUID id) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		Post post = findById(id);

		if (!post.getLikes().contains(userId)) {
			post.getLikes().add(userId);
			postRepository.save(post);
		} else {
			throw new BusinessRuleException("You already liked this post");
		}
	}

	public void unlikePost(UUID id) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		Post post = findById(id);

		if (post.getLikes().contains(userId)) {
			post.getLikes().remove(userId);
			postRepository.save(post);
		} else {
			throw new BusinessRuleException("You don't have liked this post");
		}
	}

	@Override
	public void updatePost(UUID id, UpdatePostRequest postRequest, MultipartFile photo) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		Post post = findById(id);
		verifyUserPermissionToModifyData(userId, post);

		post.setTitle(postRequest.title);
		post.setDescription(postRequest.description);
		post.setImage(postRequest.isImage);

		if (!isEmpty(photo) && postRequest.isImage == true) {
			String filename = post.getId() + "."
					+ photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
			try {
				post.setDescription(fileUploadService.upload(photo, filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		post.setUpdatedAt(LocalDateTime.now());
		postRepository.save(post);
	}

	@Override
	@Transactional
	public void deletePost(UUID id) {
		UUID userId = UUID.fromString((String) RequestContextHolder.getRequestAttributes().getAttribute("userId",
				RequestAttributes.SCOPE_REQUEST));
		Post post = findById(id);
		verifyUserPermissionToModifyData(userId, post);

		commentService.deletePostCommentSection(post.getId());
		postRepository.delete(post);
	}

	private Post findById(UUID id) {
		Optional<Post> post = postRepository.findById(id);

		if (!post.isPresent()) {
			throw new NotFoundException("Post with ID: " + id + " not found");
		}

		return post.get();
	}

	private void verifyUserPermissionToModifyData(UUID userId, Post post) {
		if (!userId.equals(post.getUserId())) {
			throw new ForbiddenException("You don't have permission to change the post content");
		}
	}

	private String generateDescription(Post post) {
		if (post.isImage() == true) {
			if (apiEnv.equals("container")) {
				return "http://localhost:4566" + post.getDescription();
			} else {
				return s3Url + post.getDescription();
			}
		}

		return post.getDescription();
	}
}
