package com.dtl.rms_server.dtos.hiringnews;

import java.time.LocalDate;

import com.dtl.rms_server.models.HiringNews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HiringNewsCreateDTO {
	@NotBlank(message = "'Title' is required.")
	@Size(max = 200, message = "'Title' is no more than 200 characters.")
	private String title;
	@NotNull(message = "'Due date' is required.")
	private LocalDate dueDate;
	private int quantity;
	private int salaryMin;
	private int salaryMax;
	@NotBlank(message = "'Description' is required.")
	@Size(max = 5000, message = "'Description' is no more than 5000 characters.")
	private String description;
	@NotBlank(message = "'Benefits' is required.")
	@Size(max = 5000, message = "'Benefits' is no more than 5000 characters.")
	private String benefits;
	@NotBlank(message = "'Requirements' is required.")
	@Size(max = 5000, message = "'Requirements' is no more than 5000 characters.")
	private String requirements;
	@NotNull(message = "'Category' is required.")
	private Long categoryId;

	public HiringNews toHiringNews() {
		return HiringNews.builder().title(this.title).dueDate(this.dueDate)
				.quantity(this.quantity).salaryMin(this.salaryMin)
				.salaryMax(this.salaryMax).description(this.description)
				.requirements(this.requirements).benefits(this.benefits)
				.build();
	}
}
