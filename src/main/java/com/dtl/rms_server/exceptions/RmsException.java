package com.dtl.rms_server.exceptions;

import org.springframework.http.HttpStatus;

public class RmsException extends RuntimeException {
	private HttpStatus httpStatusShouldReturn;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public RmsException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatusShouldReturn = httpStatus;
	}

	public RmsException(String message) {
		super(message);
	}

	public HttpStatus getHttpStatusShouldReturn() {
		return httpStatusShouldReturn;
	}

}
