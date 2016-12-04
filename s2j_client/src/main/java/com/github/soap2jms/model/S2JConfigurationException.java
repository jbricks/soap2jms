package com.github.soap2jms.model;

public class S2JConfigurationException extends S2JException {

	/**
	 *
	 */
	private static final long serialVersionUID = -2744675911702559397L;



	public S2JConfigurationException(final String message) {
		super(message, true);
	}

	public S2JConfigurationException(final String message, final Throwable cause) {
		super(message, cause, true);
	}

	public S2JConfigurationException(final Throwable cause) {
		super(cause, true);
	}

}
