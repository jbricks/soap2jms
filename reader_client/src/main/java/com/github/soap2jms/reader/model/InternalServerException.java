package com.github.soap2jms.reader.model;

public class InternalServerException extends S2JException {

	private static final long serialVersionUID = -8931549885777031727L;
	
	public InternalServerException() {
		super(false);
	}

	public InternalServerException(String message, Throwable cause) {
		super(message, cause, false);
	}

	public InternalServerException(String message, boolean permanent) {
		super(message, permanent);
	}

	public InternalServerException(Throwable cause, boolean permanent) {
		super(cause, permanent);
	}

}
