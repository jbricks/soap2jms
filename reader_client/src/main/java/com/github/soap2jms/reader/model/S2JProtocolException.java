package com.github.soap2jms.reader.model;

import com.github.soap2jms.reader.common.ErrorType;

public class S2JProtocolException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 2471601740665320014L;
	private final ErrorType errorType;

	public S2JProtocolException(final ErrorType errorType) {
		this.errorType = errorType;
	}

	public S2JProtocolException(final ErrorType errorType, final String message) {
		this.errorType = errorType;
	}

	public S2JProtocolException(final ErrorType errorType, final String message, final Throwable cause) {
		this.errorType = errorType;
	}

	public S2JProtocolException(final ErrorType errorType, final Throwable cause) {
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

}
