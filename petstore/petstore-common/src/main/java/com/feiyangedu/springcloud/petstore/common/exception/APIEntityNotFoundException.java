package com.feiyangedu.springcloud.petstore.common.exception;

public class APIEntityNotFoundException extends APIException {

	static final String CODE = "entity:not_found";

	public APIEntityNotFoundException() {
		super(CODE);
	}

	public APIEntityNotFoundException(String message) {
		super(CODE, message);
	}

	public APIEntityNotFoundException(String message, Throwable cause) {
		super(CODE, message, cause);
	}

	public APIEntityNotFoundException(Throwable cause) {
		super(CODE, "", cause);
	}

}
