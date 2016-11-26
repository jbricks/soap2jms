package com.github.soap2jms.common;

public class S2JProtocolException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 2471601740665320014L;
	private final StatusCodeEnum errorType;

	public S2JProtocolException(final StatusCodeEnum errorType) {
		this.errorType = errorType;
	}

	public S2JProtocolException(final StatusCodeEnum errorType, final String message) {
		this.errorType = errorType;
	}

	public S2JProtocolException(final StatusCodeEnum errorType, final String message, final Throwable cause) {
		this.errorType = errorType;
	}

	public S2JProtocolException(final StatusCodeEnum errorType, final Throwable cause) {
		this.errorType = errorType;
	}

	public StatusCodeEnum getErrorType() {
		return this.errorType;
	}

}
