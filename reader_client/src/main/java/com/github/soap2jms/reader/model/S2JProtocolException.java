package com.github.soap2jms.reader.model;

import com.github.soap2jms.reader.common.ErrorType;

public class S2JProtocolException extends S2JException {
	/**
	 *
	 */
	private static final long serialVersionUID = 2471601740665320014L;
	private final ErrorType errorType;

	public S2JProtocolException(final ErrorType errorType) {
		super(true);
		this.errorType = errorType;
	}

	public S2JProtocolException(final ErrorType errorType, final String message) {
		super(message, true);
		this.errorType = errorType;
	}

	public S2JProtocolException(final ErrorType errorType, final String message, final Throwable cause) {
		super(message, cause, true);
		this.errorType = errorType;
	}

	public S2JProtocolException(final ErrorType errorType, final Throwable cause) {
		super(cause, true);
		this.errorType = errorType;
	}

}
