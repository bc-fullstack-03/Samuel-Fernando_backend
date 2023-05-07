package com.samuelfernando.sysmapparrot.modules.comment.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.comment.model.UserComment;
import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;

public class UserCommentResponse {
	public UUID id;
	public String description;
	public UUID postId;
	public UUID userId;
	public UserProfile userProfile;
	public Set<UUID> likes;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	
	public static UserCommentResponse of(UUID postId, UserComment userComment) {
		UserCommentResponse response = new UserCommentResponse();
		
		response.id = userComment.getId();
		response.description = userComment.getDescription();
		response.postId = postId;
		response.userId = userComment.getId();
		response.userProfile = userComment.getUserProfile();
		response.likes = userComment.getLikes();
		response.createdAt = userComment.getCreatedAt();
		response.updatedAt = userComment.getUpdatedAt();
		
		return response;
	}
}
