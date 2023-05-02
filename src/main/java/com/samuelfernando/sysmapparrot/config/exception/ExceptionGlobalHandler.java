package com.samuelfernando.sysmapparrot.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<?> handleBusinessRuleException(BusinessRuleException businessRuleException) {
		ExceptionDetails details = new ExceptionDetails();
		details.setStatus(HttpStatus.BAD_REQUEST.value());
		details.setMessage(businessRuleException.getMessage());
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException notFoundException) {
		ExceptionDetails details = new ExceptionDetails();
		details.setStatus(HttpStatus.NOT_FOUND.value());
		details.setMessage(notFoundException.getMessage());
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}
}
