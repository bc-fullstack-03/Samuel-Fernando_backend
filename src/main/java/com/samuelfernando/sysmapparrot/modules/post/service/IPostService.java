package com.samuelfernando.sysmapparrot.modules.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.post.dto.CreatePostRequest;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponse;
import com.samuelfernando.sysmapparrot.modules.post.dto.PostResponsePage;
import com.samuelfernando.sysmapparrot.modules.post.dto.UpdatePostRequest;
import com.samuelfernando.sysmapparrot.modules.profile.dto.UserProfileResponse;

public interface IPostService {
	public List<PostResponse> getMyPosts();
	public List<PostResponse> getAllPostsByUserId(UUID userId);
	public PostResponse getPostById(UUID id);
	public PostResponsePage getFeed(UserProfileResponse userProfileResponse, int page);
	public void createPost(CreatePostRequest post, MultipartFile photo);
	public void likePost(UUID id);
	public void unlikePost(UUID id);
	public void updatePost(UUID id, UpdatePostRequest post, MultipartFile photo);
	public void deletePost(UUID id);
}
