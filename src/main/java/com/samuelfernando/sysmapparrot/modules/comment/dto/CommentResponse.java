package com.samuelfernando.sysmapparrot.modules.comment.dto;

import java.util.List;
import java.util.UUID;

import com.samuelfernando.sysmapparrot.modules.comment.model.UserComment;

public class CommentResponse {
	public UUID postId;
	public List<UserComment> userComment;
	
	public CommentResponse() {
	}

	public CommentResponse(UUID postId, List<UserComment> userComment) {
		this.postId = postId;
		this.userComment = userComment;
	}
}
