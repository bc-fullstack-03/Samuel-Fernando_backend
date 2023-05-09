package com.samuelfernando.sysmapparrot.modules.comment.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserComment {
	private UUID id;
	@NotBlank(message = "description cant be blank")
	@Size(min = 3, message = "description must contain at least 3 characters")
	private String description;
	private UUID userId;
	private UserProfile userProfile;
	private Set<UUID> likes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public UserComment() {
	}

	public UserComment(String description, UUID userId, UserProfile userProfile, Set<UUID> likes, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.setId();
		this.description = description;
		this.userId = userId;
		this.userProfile = userProfile;
		this.likes = likes;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId() {
		this.id = UUID.randomUUID();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Set<UUID> getLikes() {
		return likes;
	}

	public void setLikes(Set<UUID> likes) {
		this.likes = likes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "UserComment [id=" + id + ", description=" + description + ", userId=" + userId + ", userProfile="
				+ userProfile + ", likes=" + likes + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
