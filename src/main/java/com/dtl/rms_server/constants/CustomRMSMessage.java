package com.dtl.rms_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomRMSMessage {
	// Common
	SOMETHING_WENT_WRONG("Có lỗi xảy ra."),
	
	// Account
	ACCOUNT_NOT_EXIST("Tài khoản đã bị khóa hoặc không tồn tại."),

	// Category
	CATEGORY_NOT_EXIST("Danh mục tin không tồn tại."),
	
	// File
	UPLOAD_DIR_INVALID("Lỗi đường dẫn thư mục lưu file."),
	FILE_NAME_INVALID("Tên file không hợp lệ"),
	
	// Hiring news
	HIRING_NEWS_NOT_EXIST("Tin tuyển dụng không tồn tại"),
	HIRING_NEWS_EXPIRED("Tin tuyển dụng quá hạn"),
	
	// Apply Info
	APPLY_INFO_WRONG_DATA_FORMAT("Lỗi định dạng dữ liệu thông tin ứng tuyển");
	

	private final String content;
}
