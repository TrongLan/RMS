package com.dtl.rms_server.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplyInfoStatus {
	UNSEEN(0, "Chưa xem"), APPROVED(1, "Phù hợp"), DENIED(2, "Chưa phù hợp");
	private final Integer value;
	private final String description;

	public static Map<Integer, String> map() {
		return Arrays.asList(ApplyInfoStatus.values()).stream()
				.collect(Collectors.toMap(ApplyInfoStatus::getValue,
						ApplyInfoStatus::getDescription));
	}
}
