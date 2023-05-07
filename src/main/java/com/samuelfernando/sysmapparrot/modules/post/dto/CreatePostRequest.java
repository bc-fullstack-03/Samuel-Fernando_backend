package com.samuelfernando.sysmapparrot.modules.post.dto;

public class CreatePostRequest {
	public String title;
	public String description;
	public boolean isImage;
	
	public CreatePostRequest() {
	}

	public CreatePostRequest(String title, String description, boolean isImage) {
		this.title = title;
		this.description = description;
		this.isImage = isImage;
	}
}
