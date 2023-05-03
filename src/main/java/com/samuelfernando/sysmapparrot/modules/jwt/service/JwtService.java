package com.samuelfernando.sysmapparrot.modules.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	
	private Key generateKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}
}
