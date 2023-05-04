package com.samuelfernando.sysmapparrot.config;

public enum PublicEndpoints {
	USER_CREATE("/api/v1/user"),
	USER_AUTHENTICATION("/api/v1/authentication");
	private final String publicEndpoint;
	
	PublicEndpoints(String publicEndpoint) {
		this.publicEndpoint = publicEndpoint;
	}

	public String getPublicEndpoint() {
		return publicEndpoint;
	}
}