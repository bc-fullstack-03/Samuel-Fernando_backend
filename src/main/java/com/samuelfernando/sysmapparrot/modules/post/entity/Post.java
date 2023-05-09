package com.samuelfernando.sysmapparrot.modules.post.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Post {
	@Id
	private UUID id;
	@NotBlank(message = "title cant be blank")
	@Size(min = 3, message = "title must contain at least 3 characters")
	private String title;
	private String description;
	private UUID userId;
	private UserProfile userProfile;
	private Set<UUID> likes;
	@NotNull(message = "isImage property is required")
	private boolean isImage;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Post() {
	}

	public Post(String title, String description, UUID userId, UserProfile userProfile, Set<UUID> likes,
			boolean isImage, LocalDateTime createdAt, LocalDateTime updatedAt) {
		setId();
		this.title = title;
		this.description = description;
		this.userId = userId;
		this.userProfile = userProfile;
		this.likes = likes;
		this.isImage = isImage;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId() {
		this.id = UUID.randomUUID();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
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
}
