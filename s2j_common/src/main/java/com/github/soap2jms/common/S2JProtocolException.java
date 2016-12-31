package com.github.soap2jms.common;

public class S2JProtocolException extends S2JException {
	/**
	 *
	 */
	private static final long serialVersionUID = 2471601740665320014L;

	public S2JProtocolException(final StatusCodeEnum errorType) {
		super(errorType);
	}

	public S2JProtocolException(final StatusCodeEnum errorType, final String message) {
		super(errorType, message);
	}

	public S2JProtocolException(final StatusCodeEnum errorType, final String message, final Throwable cause) {
		super(errorType, message, cause);
	}

}
