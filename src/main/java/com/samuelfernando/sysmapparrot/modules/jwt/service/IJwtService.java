package com.samuelfernando.sysmapparrot.modules.jwt.service;

import java.util.UUID;

public interface IJwtService {
	String generateToken(UUID userId);
}
