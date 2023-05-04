package com.samuelfernando.sysmapparrot.config.interceptor;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.samuelfernando.sysmapparrot.config.PublicEndpoints;
import com.samuelfernando.sysmapparrot.modules.jwt.service.IJwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private IJwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (isOptions(request)) {
			return true;
		}

		if (isPublicEndpoint(request)) {
			return true;
		}
		
		String token = request.getHeader(AUTHORIZATION);
		String userId = jwtService.validateToken(token);

		request.setAttribute("userId", userId);

		return true;
	}

	private boolean isPublicEndpoint(HttpServletRequest request) {
		return Arrays
				.stream(PublicEndpoints.values())
				.anyMatch(publicEndpoint -> {
					Pattern pattern = Pattern.compile(publicEndpoint.getPublicEndpoint());
					String requestWithMethod = request.getMethod() + request.getRequestURI();
					boolean match = pattern.matcher(requestWithMethod).find();
					
					return match;
				});
	}

	private boolean isOptions(HttpServletRequest request) {
		return HttpMethod.OPTIONS.name().equals(request.getMethod());
	}
}
