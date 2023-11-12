package com.dtl.rms_server.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplyInfoStatus {
	UNSEEN(0), APPROVED(1), DENIED(2);
	private final Integer value;
}
