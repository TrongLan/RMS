package com.dtl.rms_server.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RmsExceptionHandler {

	@ExceptionHandler(value = {RmsException.class})
	public ResponseEntity<Object> handlingRmsException(RmsException e) {
		return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e,
				LocalDateTime.now(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handlingValidationException(
			MethodArgumentNotValidException e) {
		String errorMessage = e.getBindingResult().getAllErrors().get(0)
				.toString().split(";")[0];
		return new ResponseEntity<>(new ExceptionResponse(errorMessage, e,
				LocalDateTime.now(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

}
