package com.feiyangedu.springcloud.petstore.common.rest;

import com.feiyangedu.springcloud.petstore.common.exception.APIException;

public class APIErrorResponse {

	public final String code;
	public final String message;

	public APIErrorResponse(APIException e) {
		this.code = e.code;
		this.message = e.getMessage();
	}

}
