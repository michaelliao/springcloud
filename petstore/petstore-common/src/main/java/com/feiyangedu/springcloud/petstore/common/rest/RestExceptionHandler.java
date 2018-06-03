package com.feiyangedu.springcloud.petstore.common.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.feiyangedu.springcloud.petstore.common.exception.APIException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIErrorResponse handleRestException(Exception ex) {
		APIException e = null;
		if (ex instanceof APIException) {
			e = (APIException) ex;
		} else {
			e = new APIException(APIException.UNKNOWN_INTERNAL_ERROR, ex.getMessage(), ex);
		}
		return new APIErrorResponse(e);
	}

}
