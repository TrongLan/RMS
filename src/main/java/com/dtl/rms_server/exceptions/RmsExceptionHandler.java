package com.dtl.rms_server.exceptions;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;

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
		String errorMessage = Objects.isNull(e.getFieldError())
				? "Something went wrong."
				: e.getFieldError().getDefaultMessage();
		return new ResponseEntity<>(new ExceptionResponse(errorMessage, e,
				LocalDateTime.now(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {TokenExpiredException.class})
	public ResponseEntity<Object> handlingTokenExpiredException(
			TokenExpiredException e) {
		return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e,
				LocalDateTime.now(), HttpStatus.FORBIDDEN),
				HttpStatus.FORBIDDEN);
	}

}
