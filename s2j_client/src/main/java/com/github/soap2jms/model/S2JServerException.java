package com.github.soap2jms.model;

/**
 * An exception happened server side while operating (none of the messages was
 * delivered/acknowledge)
 */
public class S2JServerException extends S2JException {

	private static final long serialVersionUID = -8931549885777031727L;

	public S2JServerException() {
		super(false);
	}

	public S2JServerException(final String message, final boolean permanent) {
		super(message, permanent);
	}

	public S2JServerException(final String message, final Throwable cause) {
		super(message, cause, false);
	}

	public S2JServerException(final Throwable cause, final boolean permanent) {
		super(cause, permanent);
	}

}
