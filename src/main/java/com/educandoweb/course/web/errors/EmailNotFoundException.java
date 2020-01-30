package com.educandoweb.course.web.errors;

public class EmailNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException() {
        super("Email address not registered", "userManagement", "emailexists");
    }
}
