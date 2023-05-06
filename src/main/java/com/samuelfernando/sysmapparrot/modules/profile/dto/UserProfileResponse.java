package com.samuelfernando.sysmapparrot.modules.profile.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.user.entity.User;

public class UserProfileResponse {
	public UUID idUser;
	public String name;
	public String photoUri;
	public Set<UUID> following;
	public Set<UUID> followers;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;
	
	public UserProfileResponse() {
	}

	public UserProfileResponse(UUID idUser, String name, String photoUri, Set<UUID> following, Set<UUID> followers, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.idUser = idUser;
		this.name = name;
		this.following = following;
		this.followers = followers;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public static UserProfileResponse of(User user) {
		UserProfileResponse response = new UserProfileResponse();
		response.idUser = user.getId();
		response.name = user.getUserProfile().getName();
		response.photoUri = user.getUserProfile().getPhotoUri();
		response.following = user.getUserProfile().getFollowing();
		response.followers = user.getUserProfile().getFollowers();
		response.createdAt = user.getUserProfile().getCreatedAt();
		response.updatedAt = user.getUserProfile().getUpdatedAt();
		
		return response;
	}
}
