package com.github.soap2jms.reader.model;

public class S2JException extends Exception {

	private final boolean permanent;
	public S2JException(boolean permanent) {
		this.permanent=permanent;
	}

	public S2JException(String message, Throwable cause, boolean permanent) {
		super(message, cause);
		this.permanent=permanent;
	}

	public S2JException(String message, boolean permanent) {
		super(message);
		this.permanent=permanent;
	}

	public S2JException(Throwable cause, boolean permanent) {
		super(cause);
		this.permanent=permanent;
	}

	public boolean isPermanent() {
		return permanent;
	}

}
