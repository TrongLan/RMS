package com.dtl.rms_server.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTToPrincipalConverter {

	public UserPrincipal convert(DecodedJWT decodedJWT) {
		return UserPrincipal.builder()
				.accountId(Integer.valueOf(decodedJWT.getSubject()))
				.email(decodedJWT.getClaim("e").asString()).build();
	}
}
