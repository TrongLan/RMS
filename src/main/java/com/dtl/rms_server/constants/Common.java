package com.dtl.rms_server.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Common {
	ACTIVE(1), LOCKED(0);
	private final int value;
}
