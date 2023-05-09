package com.samuelfernando.sysmapparrot.modules.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePostRequest {
	@NotBlank(message = "Name cant be blank")
	@Size(min = 3, message = "title must contain at least 3 characters")
	public String title;
	public String description;
	@NotNull(message = "isImage property is required")
	public boolean isImage;
	
	public UpdatePostRequest() {
	}

	public UpdatePostRequest(String title, String description, boolean isImage) {
		this.title = title;
		this.description = description;
		this.isImage = isImage;
	}
}
