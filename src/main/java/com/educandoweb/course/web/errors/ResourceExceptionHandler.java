package com.educandoweb.course.web.errors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(BadRequestAlertException.class)
	public ResponseEntity<StandardError> objectNotFound(BadRequestAlertException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
}