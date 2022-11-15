package com.unam.cienciastop.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ApiException.class)
	protected ResponseEntity<Object> handleApiException(ApiException exception){
		
		ExceptionResponse response = new ExceptionResponse();
		
		response.setMessage(exception.getMessage());
		response.setStatus(exception.getStatus().value());
		response.setError(exception.getStatus());
		
		return new ResponseEntity<>(response, response.getError());
	}
}
