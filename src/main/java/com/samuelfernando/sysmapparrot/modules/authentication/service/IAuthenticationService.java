package com.samuelfernando.sysmapparrot.modules.authentication.service;

import com.samuelfernando.sysmapparrot.modules.authentication.dto.AuthenticateRequest;
import com.samuelfernando.sysmapparrot.modules.authentication.dto.AuthenticateResponse;

public interface IAuthenticationService {
	AuthenticateResponse authenticate(AuthenticateRequest request);
}
