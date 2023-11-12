package com.dtl.rms_server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtl.rms_server.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/common/categories")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<Object> getAllCategories() {
		return new ResponseEntity<>(categoryService.getAllCategories(),
				HttpStatus.OK);
	}
}
