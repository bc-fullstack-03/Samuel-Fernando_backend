package com.samuelfernando.sysmapparrot.modules.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {
	@NotBlank(message = "title cant be blank")
	@Size(min = 3, message = "title must contain at least 3 characters")
	public String title;
	public String description;
	@NotNull(message = "isImage property is required")
	public boolean isImage;
	
	public CreatePostRequest() {
	}

	public CreatePostRequest(String title, String description, boolean isImage) {
		this.title = title;
		this.description = description;
		this.isImage = isImage;
	}
}
