package com.github.soap2jms.queue;

import com.github.soap2jms.common.StatusCodeEnum;

public class IdAndStatus {

	private final String jmsCode;
	private final String reason;
	private final StatusCodeEnum statusCode;

	public IdAndStatus(final StatusCodeEnum statusCode, final String jmsCode, final String reason) {
		this.statusCode = statusCode;
		this.jmsCode = jmsCode;
		this.reason = reason;
	}

	public IdAndStatus(final String messageId) {
		this.jmsCode = messageId;
		this.statusCode = StatusCodeEnum.OK;
		this.reason = null;
	}

	public String getJmsCode() {
		return this.jmsCode;
	}

	public String getReason() {
		return this.reason;
	}

	public StatusCodeEnum getStatusCode() {
		return this.statusCode;
	}

}
