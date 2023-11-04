package com.dtl.rms_server.exceptions;

public class RmsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public RmsException(String message) {
		super(message);
	}

}
