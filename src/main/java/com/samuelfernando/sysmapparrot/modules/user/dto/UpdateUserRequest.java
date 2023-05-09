package com.samuelfernando.sysmapparrot.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
	@NotBlank(message = "Name cant be blank")
	@Size(min = 3, message = "name must contain at least 3 characters")
	public String name;
	@Email(message = "invalid email")
	@NotBlank(message = "email cant be blank")
	public String email;
	public String oldPassword;
	public String newPassword;
	
	public UpdateUserRequest() {
	}

	public UpdateUserRequest(String name, String email, String oldPassword, String newPassword) {
		this.name = name;
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
