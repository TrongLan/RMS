package com.dtl.rms_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
	ACTIVE(1), LOCKED(0);

	private final int value;
}
