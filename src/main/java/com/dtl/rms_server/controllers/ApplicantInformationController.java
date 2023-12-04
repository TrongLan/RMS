package com.dtl.rms_server.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.dtos.applyinfo.FileInfoDTO;
import com.dtl.rms_server.exceptions.ExceptionResponse;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.ApplicantInformation;
import com.dtl.rms_server.services.ApplicantInformationService;
import com.dtl.rms_server.services.FileStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicantInformationController {
	private final ApplicantInformationService applicantInformationService;
	private final FileStorageService fileStorageService;

	@PostMapping(path = "/common/apply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
			"multipart/form-data"})
	public ResponseEntity<Object> jobApply(@RequestParam ApplyInfoCreateDTO dto,
			@RequestParam MultipartFile file) {
		String dir = fileStorageService.saveFile(file);
		dto.setCvURL(dir);
		try {
			applicantInformationService.jobApply(dto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (RmsException e) {
			fileStorageService.deleteFile(dir);
			return new ResponseEntity<>(
					new ExceptionResponse(e.getMessage(), e,
							LocalDateTime.now(), HttpStatus.BAD_REQUEST),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(path = "/hr/approve/{applyId}/{status}")
	public ResponseEntity<Object> applyInfoApprove(
			@PathVariable(required = true) String applyId,
			@PathVariable(required = true) int status) {
		applicantInformationService.updateApplyInfosStatus(List.of(applyId),
				status);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/hr/apply-info/details/{id}")
	public ResponseEntity<Object> getApplyInfoDetails(@PathVariable String id) {
		return new ResponseEntity<>(applicantInformationService.getDetails(id),
				HttpStatus.OK);
	}

	@GetMapping("/hr/apply-info/list/{id}")
	public ResponseEntity<Object> getApplyInfoList(@PathVariable String id,
			@RequestParam(defaultValue = "0") int pageNumber) {
		return new ResponseEntity<>(
				applicantInformationService.getListApplyInfo(id, pageNumber),
				HttpStatus.OK);
	}

	@GetMapping("/hr/download/{id}")
	public ResponseEntity<Object> downloadFile(@PathVariable String id) {
		ApplicantInformation details = applicantInformationService
				.getDetails(id);
		try {

			Path filePath = Paths.get(details.getCvURL()).toAbsolutePath()
					.normalize();
			Resource resource = new UrlResource(filePath.toUri());
			var fileInfo = FileInfoDTO.builder().name(resource.getFilename())
					.content(resource.getContentAsByteArray())
					.size(resource.contentLength()).build();

			if (resource.exists()) {
				return ResponseEntity.ok().body(fileInfo);
			} else {
				throw new RuntimeException("File not found " + filePath);
			}
		} catch (IOException ex) {
			throw new RuntimeException("File not found ", ex);
		}
	}
}
