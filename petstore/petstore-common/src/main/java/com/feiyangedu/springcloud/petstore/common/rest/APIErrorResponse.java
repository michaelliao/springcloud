package com.feiyangedu.springcloud.petstore.common.rest;

import com.feiyangedu.springcloud.petstore.common.exception.APIException;

public class APIErrorResponse {

	public String code;
	public String message;

	public APIErrorResponse() {
	}

	public APIErrorResponse(APIException e) {
		this.code = e.code;
		this.message = e.getMessage();
	}

}
