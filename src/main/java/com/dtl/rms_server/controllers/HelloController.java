package com.dtl.rms_server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

	@GetMapping
	public String greeting() {
		return "Hello";
	}

	@GetMapping("/test")
	public String test() {
		return "If you see this, you logged in";
	}
}
