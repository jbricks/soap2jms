package com.github.soap2jms.common;

public class S2JProviderException extends S2JException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -155650011950775147L;
	private String jmsCode;

	public S2JProviderException(StatusCodeEnum statusCode, String message, String jmsCode, Throwable cause) {
		super(statusCode, message, cause);
		this.jmsCode = jmsCode;
	}

	public String getJmsCode() {
		return jmsCode;
	}

	@Override
	public String toString() {
		return super.toString() + (jmsCode != null ? (",jmsCode=" + jmsCode + " ") : "");
	}

}
