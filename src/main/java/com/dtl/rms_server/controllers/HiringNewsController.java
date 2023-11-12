package com.dtl.rms_server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;
import com.dtl.rms_server.services.HiringNewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class HiringNewsController {

	private final HiringNewsService hiringNewsService;

	@PostMapping(value = "/hr/hiring-news", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadNews(
			@Valid @RequestBody HiringNewsCreateDTO dto) {
		return new ResponseEntity<>(hiringNewsService.uploadHiringNews(dto),
				HttpStatus.CREATED);
	}

	@GetMapping("/common/hiring-news/details/{id}")
	public ResponseEntity<Object> getNewsDetails(@PathVariable String id) {
		return new ResponseEntity<>(
				hiringNewsService.getHiringNewsDetails(id), HttpStatus.OK);
	}

	@GetMapping("/common/hiring-news/list/{id}")
	public ResponseEntity<Object> getNewsOfCategory(@PathVariable long id) {
		return new ResponseEntity<>(
				hiringNewsService.getNewsOfCategory(id), HttpStatus.OK);
	}
}
