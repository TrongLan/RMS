package com.dtl.rms_server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilePropertiesConfig {
	private String uploadDirectory;
}
