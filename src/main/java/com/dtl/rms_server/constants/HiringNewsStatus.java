package com.dtl.rms_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HiringNewsStatus {
	ACTIVE(0), LOCKED(1);

	private int value;
}
