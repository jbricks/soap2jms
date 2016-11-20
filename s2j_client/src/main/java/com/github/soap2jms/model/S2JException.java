package com.github.soap2jms.model;

public class S2JException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 6471395293356985818L;
	private final boolean permanent;

	public S2JException(final boolean permanent) {
		this.permanent = permanent;
	}

	public S2JException(final String message, final boolean permanent) {
		super(message);
		this.permanent = permanent;
	}

	public S2JException(final String message, final Throwable cause, final boolean permanent) {
		super(message, cause);
		this.permanent = permanent;
	}

	public S2JException(final Throwable cause, final boolean permanent) {
		super(cause);
		this.permanent = permanent;
	}

	public boolean isPermanent() {
		return this.permanent;
	}

}
