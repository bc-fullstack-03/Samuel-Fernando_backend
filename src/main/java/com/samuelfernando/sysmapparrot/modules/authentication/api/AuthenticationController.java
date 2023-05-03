package com.samuelfernando.sysmapparrot.modules.authentication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuelfernando.sysmapparrot.modules.authentication.dto.AuthenticateRequest;
import com.samuelfernando.sysmapparrot.modules.authentication.dto.AuthenticateResponse;
import com.samuelfernando.sysmapparrot.modules.authentication.service.IAuthenticationService;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
	@Autowired
	private IAuthenticationService authenticationService;
	
	@PostMapping("")
	public AuthenticateResponse authenticate(@RequestBody AuthenticateRequest request) {
		return authenticationService.authenticate(request);
	}
}
