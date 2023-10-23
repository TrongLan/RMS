package com.dtl.rms_server.security;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTGenerator {
	private final JWTProperties properties;

	public String generate(int accountId, String email) {
		return JWT.create().withSubject(String.valueOf(accountId))
				.withExpiresAt(Instant.now().plus(Duration.ofHours(4)))
				.withClaim("e", email)
				.sign(Algorithm.HMAC256(properties.getSecretKey()));
	}
}
