package com.dtl.rms_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HiringNewsStatus {
	ACTIVE(1), LOCKED(0);

	private int value;
}
