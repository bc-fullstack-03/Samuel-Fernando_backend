package com.samuelfernando.sysmapparrot.config;

public enum PublicEndpoints {
	USER_CREATE("POST/api/v1/user"),
	USER_AUTHENTICATION("POST/api/v1/authentication"),
	SWAGGER_UI("GET/swagger-ui/*"),
	API_DOCS("GET/v3/api-docs/*");
	private final String publicEndpoint;
	
	PublicEndpoints(String publicEndpoint) {
		this.publicEndpoint = publicEndpoint;
	}

	public String getPublicEndpoint() {
		return publicEndpoint;
	}
}