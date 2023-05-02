package com.samuelfernando.sysmapparrot.modules.user.dto;

public class CreateUserRequest {
	public String name;
	public String email;
	public String password;
	
	public CreateUserRequest() {
	}

	public CreateUserRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
