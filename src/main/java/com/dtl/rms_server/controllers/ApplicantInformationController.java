package com.dtl.rms_server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.services.ApplicantInformationService;
import com.dtl.rms_server.services.FileStorageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applicant")
@RequiredArgsConstructor
public class ApplicantInformationController {
	private final ApplicantInformationService applicantInformationService;
	private final FileStorageService fileStorageService;

	@PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = {
			"multipart/form-data"})
	@Transactional
	public ResponseEntity<Object> jobApply(@RequestParam ApplyInfoCreateDTO dto,
			@RequestParam MultipartFile file) {
		String dir = fileStorageService.saveFile(file);
		dto.setCvURL(dir);
		try {
			applicantInformationService.jobApply(dto);
		} catch (RmsException e) {
			fileStorageService.deleteFile(dir);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
