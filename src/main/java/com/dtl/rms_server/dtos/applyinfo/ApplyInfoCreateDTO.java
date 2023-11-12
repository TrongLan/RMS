package com.dtl.rms_server.dtos.applyinfo;

import com.dtl.rms_server.constants.BasicInfo;
import com.dtl.rms_server.models.ApplicantInformation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplyInfoCreateDTO {
	@NotBlank(message = "Tên không được để trống.", groups = BasicInfo.class)
	@Size(message = "Tên không được dài quá 50 ký tự.", max = 50, groups = BasicInfo.class)
	private String firstName;
	@NotBlank(message = "Họ đệm không được để trống", groups = BasicInfo.class)
	@Size(message = "Họ đệm không được dài quá 50 ký tự.", max = 50, groups = BasicInfo.class)
	private String lastName;
	@Email(message = "Email không đúng định dạng.", groups = BasicInfo.class)
	@NotBlank(message = "Email không được để trống.", groups = BasicInfo.class)
	private String email;
	@NotBlank(message = "Số điện thoại không được để trống", groups = BasicInfo.class)
	@Size(min = 10, max = 10, message = "Số điện thoại không hợp lệ.", groups = BasicInfo.class)
	@Pattern(regexp = "^[0-9]+$", message = "Số điện thoại không hợp lệ.", groups = BasicInfo.class)
	private String phoneNumber;
	private String cvURL;
	@NotBlank(message = "'Hiring news id' is required.", groups = BasicInfo.class)
	private String newsId;

	public ApplicantInformation toApplicantInformation() {
		return ApplicantInformation.builder().firstName(firstName)
				.lastName(lastName).email(email).phoneNumber(phoneNumber)
				.cvURL(cvURL).build();
	}
}
