package com.educandoweb.course.web.errors;

public class AccountResourceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AccountResourceException(String message) {
        super(message);
    }
}
