package com.samuelfernando.sysmapparrot.modules.comment.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.samuelfernando.sysmapparrot.modules.comment.model.UserComment;

public class Comment {
	@Id
	private UUID id;
	private UUID postId;
	private List<UserComment> userComments;
	
	public Comment() {
	}

	public Comment(UUID postId, List<UserComment> userComments) {
		setId();
		this.postId = postId;
		this.userComments = userComments;
	}

	public UUID getId() {
		return id;
	}

	public void setId() {
		this.id = UUID.randomUUID();
	}

	public UUID getPostId() {
		return postId;
	}

	public void setPostId(UUID postId) {
		this.postId = postId;
	}

	public List<UserComment> getUserComments() {
		return userComments;
	}

	public void setUserComments(List<UserComment> userComments) {
		this.userComments = userComments;
	}
}
