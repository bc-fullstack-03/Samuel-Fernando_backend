package com.samuelfernando.sysmapparrot.modules.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.samuelfernando.sysmapparrot.modules.profile.model.UserProfile;

public class User {
	@Id
	private UUID id;
	private String name;
	private String email;
	private String password;
	private UserProfile userProfile;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public User() {
	}

	public User(String name, String email, String password, UserProfile userProfile, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.setId();
		this.name = name;
		this.email = email;
		this.password = password;
		this.userProfile = userProfile;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return this.id;
	}
	
	protected void setId() {
		this.id = UUID.randomUUID();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

	public void setId(UUID id) {
		this.id = id;
	}
}
