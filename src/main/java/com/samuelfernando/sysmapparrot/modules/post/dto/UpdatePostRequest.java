package com.samuelfernando.sysmapparrot.modules.post.dto;

public class UpdatePostRequest {
	public String title;
	public String description;
	public boolean isImage;
	
	public UpdatePostRequest() {
	}

	public UpdatePostRequest(String title, String description, boolean isImage) {
		this.title = title;
		this.description = description;
		this.isImage = isImage;
	}
}
