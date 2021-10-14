package com.manish.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Object> handleExceptions(LoginException exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage(exception.getMessage());
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		return entity;
	}
}
