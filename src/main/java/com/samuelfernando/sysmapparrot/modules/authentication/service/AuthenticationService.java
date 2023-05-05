package com.samuelfernando.sysmapparrot.modules.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samuelfernando.sysmapparrot.config.exception.AuthenticationException;
import com.samuelfernando.sysmapparrot.modules.authentication.dto.AuthenticateRequest;
import com.samuelfernando.sysmapparrot.modules.authentication.dto.AuthenticateResponse;
import com.samuelfernando.sysmapparrot.modules.jwt.service.IJwtService;
import com.samuelfernando.sysmapparrot.modules.user.entity.User;
import com.samuelfernando.sysmapparrot.modules.user.service.IUserService;

@Service
public class AuthenticationService implements IAuthenticationService {
	@Autowired
	private IUserService userService;
	@Autowired
	private IJwtService jwtService;
	@Autowired
	private PasswordEncoder bcryptPasswordEncoder;
	
	@Override
	public AuthenticateResponse authenticate(AuthenticateRequest request) {
		User user = userService.findUserByEmail(request.email);
		
		if (user == null || !bcryptPasswordEncoder.matches(request.password, user.getPassword())) {
			throw new AuthenticationException("Email or password are incorrect");
		}
		
		String token = jwtService.generateToken(user.getId());
		
		
		return new AuthenticateResponse(user.getId(), token);
	}

}
