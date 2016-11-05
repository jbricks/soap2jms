package com.github.soap2jms.reader.model;

public class InternalServerException extends S2JException {

	private static final long serialVersionUID = -8931549885777031727L;

	public InternalServerException() {
		super(false);
	}

	public InternalServerException(final String message, final boolean permanent) {
		super(message, permanent);
	}

	public InternalServerException(final String message, final Throwable cause) {
		super(message, cause, false);
	}

	public InternalServerException(final Throwable cause, final boolean permanent) {
		super(cause, permanent);
	}

}
