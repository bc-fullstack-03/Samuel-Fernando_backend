package com.samuelfernando.sysmapparrot.modules.comment.service;

import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentRequest;
import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.dto.UserCommentResponse;

public interface ICommentService {
	public void createPostCommentSection(UUID postId);
	public CommentResponse getComments(UUID postId);
	public UserCommentResponse getComment(UUID postId, UUID commentId);
	public void likePostComment(UUID postId, UUID commentId);
	public void unlikePostComment(UUID postId, UUID commentId);
	public void insertPostComment(UUID postId, CommentRequest userCommentRequest);
	public void updateUserComment(UUID postId, UUID userCommentId, CommentRequest userCommentRequest);
	public void deleteUserComment(UUID postId, UUID commentId);
	public void deletePostCommentSection(UUID id);
}
