package com.dtl.rms_server.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.dtl.rms_server.constants.ApplyInfoStatus;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoExportDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "applicant_information")
@Builder
public class ApplicantInformation {
	@Id
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "varchar(36)")
	private UUID id;
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;
	@Column(name = "email", length = 255, nullable = false)
	private String email;
	@Column(name = "phone_number", length = 20, nullable = false)
	private String phoneNumber;
	@Column(name = "cv_url", nullable = false)
	private String cvURL;
	@Column(name = "apply_date")
	private LocalDateTime applyDate;
	@Column(name = "status")
	private int status;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hiring_news_id")
	@JsonBackReference
	private HiringNews hiringNews;

	public ApplyInfoExportDTO toApplyInfoExportDTO() {
		return ApplyInfoExportDTO.builder().firstName(firstName)
				.lastName(lastName).applyDate(applyDate).email(email)
				.phoneNumber(phoneNumber)
				.status(ApplyInfoStatus.map().get(status)).build();
	}

}
