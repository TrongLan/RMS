package com.dtl.rms_server.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "'Email' is required.")
	@Email
    private String username;
	@NotBlank(message = "'Password' is required.")
    private String password;
}
