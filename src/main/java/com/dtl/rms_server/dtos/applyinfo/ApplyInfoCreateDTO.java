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
	@NotBlank(message = "'First name' is required.", groups = BasicInfo.class)
	@Size(message = "'First name' is no longer than 50 characters.", max = 50, groups = BasicInfo.class)
	private String firstName;
	@NotBlank(message = "'Last name' is required.", groups = BasicInfo.class)
	@Size(message = "'Last name' is no longer than 50 characters.", max = 50, groups = BasicInfo.class)
	private String lastName;
	@Email(message = "'Email' is invalid.", groups = BasicInfo.class)
	@NotBlank(message = "'Email' is required.", groups = BasicInfo.class)
	private String email;
	@NotBlank(message = "'Phone number' is required.", groups = BasicInfo.class)
	@Size(min = 10, max = 10, message = "'Phone number' is invalid.", groups = BasicInfo.class)
	@Pattern(regexp = "^[0-9]+$", message = "'Phone number' must contains digits only.", groups = BasicInfo.class)
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
