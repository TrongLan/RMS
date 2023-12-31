package com.dtl.rms_server.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "hiring_news")
@Builder
public class HiringNews {
	@Id
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "VARCHAR(36)")
	private UUID id;
	@Column(name = "title", length = 200, nullable = false)
	private String title;
	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "salary_min")
	private int salaryMin;
	@Column(name = "salary_max")
	private int salaryMax;
	@Column(name = "description", length = 5000)
	private String description;
	@Column(name = "benefits", length = 5000)
	private String benefits;
	@Column(name = "requirements", length = 5000)
	private String requirements;
	@Column(name = "status")
	private int status;
	@Column(name = "is_active")
	private int isActive;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	@JsonBackReference
	private Account account;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	@JsonManagedReference
	private Category category;
	@OneToMany(mappedBy = "hiringNews")
	@JsonManagedReference
	private List<ApplicantInformation> applicantInformations;
}
