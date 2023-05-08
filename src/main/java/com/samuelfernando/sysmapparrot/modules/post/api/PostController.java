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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.post.dto.CreatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponse;
import com.samuelfernando.sysmapparrot.modules.post.dto.UpdatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.service.IPostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
	@Autowired
	private IPostService postService;
	
	@GetMapping("")
	public List<PostResponse> getMyPosts() {
		return postService.getMyPosts();
	}

	@GetMapping("/{id}")
	public PostResponse getPostById(@PathVariable UUID id) {
		return postService.getPostById(id);
	}
	
	@GetMapping("/profile/{userId}")
	public List<PostResponse> getAllPostsByUserId(@PathVariable UUID userId) {
		return postService.getAllPostsByUserId(userId);
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
	
	@PutMapping("/{id}")
	public void updatePost(@PathVariable UUID id, @RequestPart(required = false, name = "photo") MultipartFile photo,
			@RequestPart("post") UpdatePostRequest updatePostRequest) {
		postService.updatePost(id, updatePostRequest, photo);
	}
	
	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable UUID id) {
		postService.deletePost(id);
	}
}
