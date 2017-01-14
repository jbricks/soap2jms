package com.github.soap2jms.common;

/**
 * This exception is threw when client parameters are not correct (the queue
 * doesn't exist...).
 *
 */
public class S2JConfigurationException extends S2JException {

	/**
	 *
	 */
	private static final long serialVersionUID = -2744675911702559397L;

	public S2JConfigurationException(final StatusCodeEnum statusCode, final String message) {
		super(statusCode, message);
	}

	public S2JConfigurationException(final StatusCodeEnum statusCode, final String message, final Throwable cause) {
		super(statusCode, message, cause);
	}

	public S2JConfigurationException(final StatusCodeEnum statusCode, final Throwable cause) {
		super(cause, statusCode);
	}

}
