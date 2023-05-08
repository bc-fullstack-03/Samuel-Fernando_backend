package com.samuelfernando.sysmapparrot.modules.user.dto;

public class UpdateUserRequest {
	public String name;
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
