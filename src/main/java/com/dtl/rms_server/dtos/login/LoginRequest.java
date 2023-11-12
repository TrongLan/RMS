package com.dtl.rms_server.dtos.login;

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
	@NotBlank(message = "Email không được để trống.")
	@Email(message = "Không đúng định dạng email.")
    private String username;
	@NotBlank(message = "Mật khẩu không được để trống.")
    private String password;
}
