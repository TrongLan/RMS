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
	@NotBlank(message = "Tiêu đề không được để trống.")
	@Size(max = 200, message = "Tiêu đề không dài quá 200 ký tự.")
	private String title;
	@NotNull(message = "Hạn nộp hồ sơ không để trống.")
	private LocalDate dueDate;
	private int quantity;
	private int salaryMin;
	private int salaryMax;
	@NotBlank(message = "Mô tả công việc không để trống.")
	@Size(max = 5000, message = "Mô tả công việc không dài quá 5000 ký tự.")
	private String description;
	@NotBlank(message = "Chế độ đãi ngộ không được để trống.")
	@Size(max = 5000, message = "Chế độ đãi ngộ không dài quá 5000 ký tự.")
	private String benefits;
	@NotBlank(message = "Yêu cầu ứng viên không được để trống.")
	@Size(max = 5000, message = "Yêu cầu ứng viên không dài quá 5000 ký tự.")
	private String requirements;
	@NotNull(message = "Không để trống danh mục tin.")
	private Long categoryId;

	public HiringNews toHiringNews() {
		return HiringNews.builder().title(this.title).dueDate(this.dueDate)
				.quantity(this.quantity).salaryMin(this.salaryMin)
				.salaryMax(this.salaryMax).description(this.description)
				.requirements(this.requirements).benefits(this.benefits)
				.build();
	}
}
