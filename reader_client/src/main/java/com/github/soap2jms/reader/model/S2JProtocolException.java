package com.github.soap2jms.reader.model;

import com.github.soap2jms.reader.common.ErrorType;

public class S2JProtocolException extends S2JException {
	private final ErrorType errorType;
	public S2JProtocolException(ErrorType errorType) {
		super(true);
		this.errorType = errorType;
	}

	public S2JProtocolException(ErrorType errorType, String message, Throwable cause) {
		super(message, cause, true);
		this.errorType = errorType;
	}

	public S2JProtocolException(ErrorType errorType, String message) {
		super(message, true);
		this.errorType = errorType;
	}

	public S2JProtocolException(ErrorType errorType, Throwable cause) {
		super(cause, true);
		this.errorType = errorType;
	}

}
