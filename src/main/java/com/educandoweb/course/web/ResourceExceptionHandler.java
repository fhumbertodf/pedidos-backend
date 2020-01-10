package com.educandoweb.course.web;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.ObjectNotFoundAlertException;
import com.educandoweb.course.web.errors.StandardError;
import com.educandoweb.course.web.errors.ValidationError;
import com.educandoweb.course.web.util.HeaderUtil;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@Value("${jhipster.clientApp.name}")
    private String applicationName;

	@ExceptionHandler(BadRequestAlertException.class)
	public ResponseEntity<StandardError> badRequest(BadRequestAlertException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.headers(HeaderUtil.createFailureAlert(applicationName, true, e.getEntityName(), e.getErrorKey(), e.getMessage()))
				.body(err);
	}
	
	@ExceptionHandler(ObjectNotFoundAlertException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundAlertException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.headers(HeaderUtil.createFailureAlert(applicationName, true, e.getEntityName(), e.getErrorKey(), e.getMessage()))
				.body(err);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<StandardError> objectNotFound(EmptyResultDataAccessException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.headers(HeaderUtil.createFailureAlert(applicationName, true, "", "id not found", e.getMessage()))
				.body(err);
	}	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.headers(HeaderUtil.createFailureAlert(applicationName, true, "", e.getConstraintName(), e.getMessage()))
				.body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		String entityName = "";
		
		ValidationError err = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
			entityName = x.getObjectName();
		}		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.headers(HeaderUtil.createFailureAlert(applicationName, true, entityName, "invalid field", e.getMessage()))
				.body(err);
	}
}