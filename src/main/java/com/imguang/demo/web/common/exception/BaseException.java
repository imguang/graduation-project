package com.imguang.demo.web.common.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4982423440902272096L;

	protected static final String CODE = "1";

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return CODE;
	}

}
