package com.educandoweb.course.web.errors;

public class InvalidPasswordException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Incorrect password", "", "Status.BAD_REQUEST");
    }
}
