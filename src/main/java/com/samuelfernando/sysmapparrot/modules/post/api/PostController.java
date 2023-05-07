package com.samuelfernando.sysmapparrot.modules.post.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentRequest;
import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.dto.UserCommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.service.ICommentService;
import com.samuelfernando.sysmapparrot.modules.post.dto.CreatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponse;
import com.samuelfernando.sysmapparrot.modules.post.dto.UpdatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.service.IPostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
	@Autowired
	private IPostService postService;
	@Autowired
	private ICommentService commentService;

	@GetMapping("")
	public List<PostResponse> getMyPosts() {
		return postService.getMyPosts();
	}

	@GetMapping("/{id}")
	public PostResponse getPostById(@PathVariable UUID id) {
		return postService.getPostById(id);
	}

	@GetMapping("/{id}/comments")
	public CommentResponse getComments(@PathVariable UUID id) {
		return commentService.getComments(id);
	}
	
	@GetMapping("/{id}/comments/{commentId}")
	public UserCommentResponse getComment(@PathVariable UUID id, @PathVariable UUID commentId) {
		return commentService.getComment(id, commentId);
	}


	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestPart(required = false, name = "photo") MultipartFile photo,
			@RequestPart("post") CreatePostRequest post) {
		postService.createPost(post, photo);
	}
	
	@PostMapping("/{id}/like")
	public void likePost(@PathVariable UUID id) {
		postService.likePost(id);
	}
	
	@PostMapping("/{id}/unlike")
	public void unlikePost(@PathVariable UUID id) {
		postService.unlikePost(id);
	}
	
	@PostMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertPostComment(@PathVariable UUID id, @RequestBody CommentRequest commentRequest) {
		commentService.insertPostComment(id, commentRequest);
	}

	@PutMapping("/{id}")
	public void updatePost(@PathVariable UUID id, @RequestPart(required = false, name = "photo") MultipartFile photo,
			@RequestPart("post") UpdatePostRequest updatePostRequest) {
		postService.updatePost(id, updatePostRequest, photo);
	}
	
	@PutMapping("/{id}/comments/{userCommentId}")
	public void updateUserComment(@PathVariable UUID id, @PathVariable UUID userCommentId, @RequestBody CommentRequest commentRequest) {
		commentService.updateUserComment(id, userCommentId, commentRequest);
	}
	
	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable UUID id) {
		postService.deletePost(id);
	}
	
	@DeleteMapping("/{id}/comments/{userCommentId}")
	public void deletePost(@PathVariable UUID id, @PathVariable UUID userCommentId) {
		commentService.deleteUserComment(id, userCommentId);
	}
}
