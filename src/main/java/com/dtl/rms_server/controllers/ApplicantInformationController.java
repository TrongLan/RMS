package com.dtl.rms_server.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.exceptions.ExceptionResponse;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.services.ApplicantInformationService;
import com.dtl.rms_server.services.FileStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicantInformationController {
	private final ApplicantInformationService applicantInformationService;
	private final FileStorageService fileStorageService;

	@PostMapping(path = "/apply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
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

	@PutMapping(path = "/hr/approve/{applyId}/{status}")
	public ResponseEntity<Object> applyInfoApprove(
			@PathVariable(required = true) String applyId,
			@PathVariable(required = true) int status) {
		applicantInformationService.updateApplyInfosStatus(List.of(applyId),
				status);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}
}
