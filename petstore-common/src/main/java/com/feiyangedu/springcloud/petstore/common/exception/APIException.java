package com.feiyangedu.springcloud.petstore.common.exception;

public class APIException extends RuntimeException {

	static final String UNKNOWN_INTERNAL_ERROR = "unknown:internal_error";

	public final String code;

	public APIException(String code) {
		super("Unknown internal error.");
		this.code = code;
	}

	public APIException(String code, String message) {
		super(message);
		this.code = code;
	}

	public APIException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public APIException(Throwable cause) {
		super(cause);
		this.code = UNKNOWN_INTERNAL_ERROR;
	}

}
