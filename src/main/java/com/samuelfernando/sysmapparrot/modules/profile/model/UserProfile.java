package com.samuelfernando.sysmapparrot.modules.profile.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserProfile {
	@NotBlank(message = "Name cant be blank")
	@Size(min = 3, message = "name must contain at least 3 characters")
	private String name;
	private String photoUri;
	private Set<UUID> following;
	private Set<UUID> followers;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public UserProfile() {
	}

	public UserProfile(String name, String photoUri, Set<UUID> following, Set<UUID> followers, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.name = name;
		this.photoUri = photoUri;
		this.following = following;
		this.followers = followers;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUri() {
		return photoUri;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}

	public Set<UUID> getFollowing() {
		return following;
	}

	public void setFollowing(Set<UUID> following) {
		this.following = following;
	}

	public Set<UUID> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<UUID> followers) {
		this.followers = followers;
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
