package com.dtl.rms_server.services;

import org.springframework.web.multipart.MultipartFile;

import com.dtl.rms_server.exceptions.RmsException;

public interface FileStorageService {
	void createDirectory(String folderName) throws RmsException;
	String saveFile(MultipartFile file) throws RmsException;
	void deleteFile(String directory) throws RmsException;

}
