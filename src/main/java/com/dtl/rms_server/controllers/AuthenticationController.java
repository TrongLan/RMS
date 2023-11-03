package com.dtl.rms_server.controllers;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtl.rms_server.dtos.login.LoginRequest;
import com.dtl.rms_server.dtos.login.LoginResponse;
import com.dtl.rms_server.exceptions.ExceptionResponse;
import com.dtl.rms_server.security.JWTGenerator;
import com.dtl.rms_server.security.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
	private final JWTGenerator jwtGenerator;
	private final AuthenticationManager authenticationManager;
	@PostMapping("/login")
	public ResponseEntity<Object> login(
			@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getPassword(),
							Collections.emptyList()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			var principal = (UserPrincipal) authenticate.getPrincipal();
			return new ResponseEntity<>(
					LoginResponse.builder()
							.accessToken(jwtGenerator.generate(
									principal.getAccountId(),
									principal.getEmail()))
							.build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ExceptionResponse("Sai thông tin đăng nhập", e,
							LocalDateTime.now(), null),
					HttpStatus.BAD_REQUEST);
		}

	}
}
