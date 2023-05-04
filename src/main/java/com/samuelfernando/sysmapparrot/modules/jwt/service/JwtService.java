package com.samuelfernando.sysmapparrot.modules.jwt.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.samuelfernando.sysmapparrot.config.exception.AuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IJwtService {	
	@Value("${app-config.secrets.secret-key}")
	private String SECRET_KEY;
	private final long TWO_HOURS = 7200000;
	
	public String generateToken(UUID userId) {
		return Jwts
				.builder()
				.setSubject(userId.toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TWO_HOURS))
				.signWith(generateKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String validateToken(String token) {
		String accessToken = extractToken(token);

		try {
			Claims claims = Jwts
					.parserBuilder()
					.setSigningKey(generateKey())
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
				
			String subject = claims.getSubject();
			
			if (isEmpty(subject) || claims.getExpiration().before(new Date())) {
				throw new AuthenticationException("Invalid token");
			}
					
			return subject;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AuthenticationException("Error while trying to proccess the informed access token");
		}
	}
	
	private Key generateKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}
	
	private String extractToken(String token) {
		if (isEmpty(token)) {
			throw new AuthenticationException("Access token was not informed");
		}
		if (token.contains(" ")) {
			return token.split(" ")[1];
		}
		
		return token;
	}
}
