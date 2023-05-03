package com.samuelfernando.sysmapparrot.modules.authentication.dto;

public class AuthenticateRequest {
	public String email;
	public String password;
	
	public AuthenticateRequest() {
	}
	
	public AuthenticateRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
