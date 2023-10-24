package com.dtl.rms_server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;
import com.dtl.rms_server.services.HiringNewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/hiring-news")
public class HiringNewsController {

	private final HiringNewsService hiringNewsService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadNews(
			@Valid @RequestBody HiringNewsCreateDTO dto) {
		hiringNewsService.uploadHiringNews(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
