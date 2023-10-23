package com.dtl.rms_server.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTDecoder {
	private final JWTProperties jwtProperties;

	public DecodedJWT decode(String token) {
		return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
				.build().verify(token);
	}
	
	
}
