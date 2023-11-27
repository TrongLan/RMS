package com.dtl.rms_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomRMSMessage {
	// Common
	SOMETHING_WENT_WRONG("Có lỗi xảy ra."), TOKEN_EXPIRED(
			"Hết phiên đăng nhập"), LOGIN_ERROR("Lỗi đăng nhập."),

	// Account
	ACCOUNT_NOT_EXIST("Tài khoản đã bị khóa hoặc không tồn tại."),

	// Category
	CATEGORY_NOT_EXIST("Danh mục tin không tồn tại."), CATEGORY_LOCKED(
			"Danh mục tin không hoạt động."),

	// File
	UPLOAD_DIR_INVALID("Lỗi đường dẫn thư mục lưu file."), FILE_NAME_INVALID(
			"Tên file không hợp lệ"), FILE_EXTENSION_INVALID(
					"Chỉ chấp nhận file pdf, doc, docx và html."),

	// Hiring news
	HIRING_NEWS_NOT_EXIST("Tin tuyển dụng không tồn tại"), HIRING_NEWS_EXPIRED(
			"Tin tuyển dụng quá hạn"),

	// Apply Info
	APPLY_INFO_WRONG_DATA_FORMAT(
			"Lỗi định dạng dữ liệu thông tin ứng tuyển"), APLLY_INFO_NOT_EXIST(
					"Thông tin ứng tuyển không tồn tại.");

	private final String content;
}
