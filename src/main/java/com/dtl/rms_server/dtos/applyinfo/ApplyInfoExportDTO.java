package com.dtl.rms_server.dtos.applyinfo;

import java.time.LocalDateTime;

import com.dtl.rms_server.constants.ExcelHeader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApplyInfoExportDTO {
	@ExcelHeader(name = "Họ đệm")
	private String lastName;
	@ExcelHeader(name = "Tên")
	private String firstName;
	@ExcelHeader(name = "Email")
	private String email;
	@ExcelHeader(name = "Số điện thoại")
	private String phoneNumber;
	@ExcelHeader(name = "Thời gian ứng tuyển")
	private LocalDateTime applyDate;
	@ExcelHeader(name = "Trạng thái")
	private String status;
}
