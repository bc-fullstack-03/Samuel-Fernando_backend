package com.samuelfernando.sysmapparrot.modules.comment.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentRequest;
import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.dto.UserCommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.service.ICommentService;

@RestController
@RequestMapping("/api/v1/post")
public class CommentController {
	@Autowired
	private ICommentService commentService;

	@GetMapping("/{id}/comments")
	public CommentResponse getComments(@PathVariable UUID id) {
		return commentService.getComments(id);
	}

	@GetMapping("/{id}/comments/{commentId}")
	public UserCommentResponse getComment(@PathVariable UUID id, @PathVariable UUID commentId) {
		return commentService.getComment(id, commentId);
	}

	@PostMapping("/{id}/comments/{commentId}/like")
	public void likePostComment(@PathVariable UUID id, @PathVariable UUID commentId) {
		commentService.likePostComment(id, commentId);
	}

	@PostMapping("/{id}/comments/{commentId}/unlike")
	public void unlikePostComment(@PathVariable UUID id, @PathVariable UUID commentId) {
		commentService.unlikePostComment(id, commentId);
	}

	@PostMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertPostComment(@PathVariable UUID id, @RequestBody @Validated CommentRequest commentRequest) {
		commentService.insertPostComment(id, commentRequest);
	}

	@PutMapping("/{id}/comments/{userCommentId}")
	public void updateUserComment(@PathVariable UUID id, @PathVariable UUID userCommentId,
			@RequestBody @Validated CommentRequest commentRequest) {
		commentService.updateUserComment(id, userCommentId, commentRequest);
	}

	@DeleteMapping("/{id}/comments/{userCommentId}")
	public void deletePost(@PathVariable UUID id, @PathVariable UUID userCommentId) {
		commentService.deleteUserComment(id, userCommentId);
	}
}
