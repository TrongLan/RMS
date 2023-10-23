package com.dtl.rms_server.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionResponse {
	private final String message;
	@JsonIgnore
	private final Throwable throwable;
	private final LocalDateTime dateTime;
	private final HttpStatus httpStatus;

}
