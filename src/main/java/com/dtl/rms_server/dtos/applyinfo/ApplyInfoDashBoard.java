package com.dtl.rms_server.dtos.applyinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyInfoDashBoard {
	private long total;
	private long suitable;
	private long notSuitable;
	private long pending;

}
