package com.samuelfernando.sysmapparrot.modules.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserProfileRequest {
	@NotBlank(message = "name cant be blank")
	@Size(min = 3, message = "name must contain at least 3 characters")
	public String name;
	
	public UpdateUserProfileRequest() {
	}

	public UpdateUserProfileRequest(String name) {
		this.name = name;
	}
}
