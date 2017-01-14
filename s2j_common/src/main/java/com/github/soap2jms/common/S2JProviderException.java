package com.github.soap2jms.common;

public class S2JProviderException extends S2JException {
	/**
	 *
	 */
	private static final long serialVersionUID = -155650011950775147L;
	private final String jmsCode;

	public S2JProviderException(final StatusCodeEnum statusCode, final String message, final String jmsCode,
			final Throwable cause) {
		super(statusCode, message, cause);
		this.jmsCode = jmsCode;
	}

	public String getJmsCode() {
		return this.jmsCode;
	}

	@Override
	public String toString() {
		return super.toString() + (this.jmsCode != null ? (",jmsCode=" + this.jmsCode + " ") : "");
	}

}
