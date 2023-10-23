package com.dtl.rms_server.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JWTProperties {
	private String secretKey;
}
