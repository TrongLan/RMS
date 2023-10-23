package com.dtl.rms_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JWTAuthenticationFilter authenticationFilter;
	private final CustomUserDetailService customUserDetailService;

	@Bean
	SecurityFilterChain applicationSecurity(HttpSecurity http)
			throws Exception {
		http.addFilterBefore(authenticationFilter,
				UsernamePasswordAuthenticationFilter.class);

		http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(t -> t
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(t -> t.disable()).securityMatcher("/**")
				.authorizeHttpRequests(
						t -> t.requestMatchers("/", "/auth/login").permitAll()
								.anyRequest().authenticated());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http)
			throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(customUserDetailService)
				.passwordEncoder(passwordEncoder()).and().build();
	}
}
