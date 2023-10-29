package com.dtl.rms_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomRMSMessage {
	// Account
	ACCOUNT_NOT_EXIST("Tài khoản đã bị khóa hoặc không tồn tại."),

	// Category
	CATEGORY_NOT_EXIST("Danh mục tin không tồn tại.");

	private final String content;
}
