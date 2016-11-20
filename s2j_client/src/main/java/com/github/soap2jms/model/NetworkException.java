package com.github.soap2jms.model;

public class NetworkException extends S2JException {

	/**
	 *
	 */
	private static final long serialVersionUID = -2744675911702559397L;

	public NetworkException(final boolean permanent) {
		super(permanent);
		// TODO Auto-generated constructor stub
	}

	public NetworkException(final String message, final boolean permanent) {
		super(message, permanent);
		// TODO Auto-generated constructor stub
	}

	public NetworkException(final String message, final Throwable cause, final boolean permanent) {
		super(message, cause, permanent);
		// TODO Auto-generated constructor stub
	}

	public NetworkException(final Throwable cause, final boolean permanent) {
		super(cause, permanent);
		// TODO Auto-generated constructor stub
	}

}
