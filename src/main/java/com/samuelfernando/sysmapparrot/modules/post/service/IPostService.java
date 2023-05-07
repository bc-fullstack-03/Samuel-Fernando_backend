package com.samuelfernando.sysmapparrot.modules.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.post.dto.CreatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponse;
import com.samuelfernando.sysmapparrot.modules.post.dto.UpdatePostRequest;

public interface IPostService {
	public List<PostResponse> getMyPosts();
	public PostResponse getPostById(UUID id);
	public void createPost(CreatePostRequest post, MultipartFile photo);
	public void updatePost(UUID id, UpdatePostRequest post, MultipartFile photo);
	public void deletePost(UUID id);
}
