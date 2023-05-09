package com.samuelfernando.sysmapparrot.modules.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {
	@NotBlank(message = "description cant be blank")
	@Size(min = 3, message = "description must contain at least 3 characters")
	public String description;

	public CommentRequest() {
	}

	public CommentRequest(String description) {
		this.description = description;
	}
}
