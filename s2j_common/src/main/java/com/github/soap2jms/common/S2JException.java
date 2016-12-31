package com.github.soap2jms.common;

public class S2JException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 6471395293356985818L;
	private final StatusCodeEnum statusCode;

	public S2JException(final StatusCodeEnum statusCode) {
		this.statusCode = statusCode;
	}

	public S2JException(final StatusCodeEnum statusCode, final String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public S2JException(final StatusCodeEnum statusCode, final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public S2JException(final Throwable cause, final StatusCodeEnum statusCode) {
		super(cause);
		this.statusCode = statusCode;
	}

	public StatusCodeEnum getStatusCode() {
		return this.statusCode;
	}

	@Override
	public String toString() {
		return super.toString() + ",statusCode=" + statusCode + " ";
	}

}
