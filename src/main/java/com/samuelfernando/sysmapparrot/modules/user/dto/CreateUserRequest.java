package com.samuelfernando.sysmapparrot.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
	@NotBlank(message = "Name cant be blank")
	@Size(min = 3, message = "name must contain at least 3 characters")
	public String name;
	@Email
	@NotBlank(message = "email cant be blank")
	public String email;
	@NotBlank(message = "password cant be blank")
	@Size(min = 3, message = "password must contain at least 3 characters")
	public String password;
	
	public CreateUserRequest() {
	}

	public CreateUserRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
