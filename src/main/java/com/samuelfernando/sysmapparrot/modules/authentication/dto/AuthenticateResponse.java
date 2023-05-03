package com.samuelfernando.sysmapparrot.modules.authentication.dto;

import java.util.UUID;

public class AuthenticateResponse {
	public UUID userId;
	public String token;
	
	public AuthenticateResponse() {
	}

	public AuthenticateResponse(UUID userId, String token) {
		this.userId = userId;
		this.token = token;
	}
}
