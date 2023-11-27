package com.dtl.rms_server.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dtl.rms_server.configuration.FilePropertiesConfig;
import com.dtl.rms_server.constants.CustomRMSMessage;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.services.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {
	private final FilePropertiesConfig filePropertiesConfig;

	@Override
	public String saveFile(MultipartFile file) throws RmsException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String[] strings = fileName.split("\\.");
		String ext = strings[strings.length - 1];
		if (fileName.contains("..")) {
			throw new RmsException(
					CustomRMSMessage.FILE_NAME_INVALID.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		List<String> fileAllowed = List.of("pdf", "docx", "doc", "html");
		if (!fileAllowed.contains(ext)) {
			throw new RmsException(
					CustomRMSMessage.FILE_EXTENSION_INVALID.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		try {
			// Copy file to the target location (Replacing existing file with
			// the same name)
			Path path = Paths.get(filePropertiesConfig.getUploadDirectory())
					.toAbsolutePath().normalize();

			Path targetLocation = path.resolve(UUID.randomUUID() + "." + ext);
			Files.copy(file.getInputStream(), targetLocation,
					StandardCopyOption.REPLACE_EXISTING);
			log.info("New file directory: {}", targetLocation.toString());
			return targetLocation.toString();
		} catch (IOException ex) {
			throw new RmsException(
					"Không tồn tại đường dẫn: " + ex.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public void createDirectory(String folderName) throws RmsException {
		try {
			Files.createDirectories(
					Paths.get(filePropertiesConfig.getUploadDirectory()
							+ folderName.trim()).toAbsolutePath().normalize());
			log.info("A new folder name '{}' has been created.", folderName);
		} catch (Exception ex) {
			throw new RmsException(
					CustomRMSMessage.UPLOAD_DIR_INVALID.getContent(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public void deleteFile(String directory) throws RmsException {
		try {
			Files.delete(Paths.get(directory));
			log.info("Directory '{}' has been deleted since upload API failed.",
					directory);
		} catch (IOException e) {
			log.info("Directory invalid or file cannot be deleted.");
			throw new RmsException(
					CustomRMSMessage.SOMETHING_WENT_WRONG.getContent(),
					HttpStatus.BAD_REQUEST);
		}

	}

}
