package com.feiyangedu.springcloud.petstore.common.context;

public class NoUserContextException extends RuntimeException {

	public NoUserContextException() {
	}

	public NoUserContextException(String message) {
		super(message);
	}

	public NoUserContextException(Throwable cause) {
		super(cause);
	}

	public NoUserContextException(String message, Throwable cause) {
		super(message, cause);
	}

}
