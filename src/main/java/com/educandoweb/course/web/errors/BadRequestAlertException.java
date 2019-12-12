package com.educandoweb.course.web.errors;

public class BadRequestAlertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestAlertException(String message) {
		super(message);
	}

	public BadRequestAlertException(String message, Throwable cause) {
		super(message, cause);
	}
}