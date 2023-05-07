package com.samuelfernando.sysmapparrot.modules.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.comment.dto.CommentResponse;
import com.samuelfernando.sysmapparrot.modules.comment.model.UserComment;
import com.samuelfernando.sysmapparrot.modules.post.entity.Post;
import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;

public class PostResponse {
	public UUID id;
	public String title;
	public String description;
	public UUID userId;
	public UserProfile userProfile;
	public Set<UUID> likes;
	public boolean isImage;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	public List<UserComment> comments;  
	
	public PostResponse() {
	}
	
	public static PostResponse of(Post post, CommentResponse commentResponse) {
		PostResponse response = new PostResponse();
		
		response.id = post.getId();
		response.title = post.getTitle();
		response.description = post.getDescription();
		response.userId = post.getUserId();
		response.userProfile = post.getUserProfile();
		response.likes = post.getLikes();
		response.isImage = post.isImage();
		response.createdAt = post.getCreatedAt();
		response.updatedAt = post.getUpdatedAt();
		response.comments = commentResponse.userComment;
		
		return response;
	}
}
