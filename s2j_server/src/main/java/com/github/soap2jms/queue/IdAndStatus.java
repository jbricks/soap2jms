package com.github.soap2jms.queue;

import com.github.soap2jms.common.StatusCodeEnum;

public class IdAndStatus {

	private final String messageId;
	private final String reason;
	private final StatusCodeEnum statusCode;

	public IdAndStatus(final StatusCodeEnum statusCode, final String messageId, final String reason) {
		this.statusCode = statusCode;
		this.messageId = messageId;
		this.reason = reason;
	}

	public IdAndStatus(final String messageId) {
		this.messageId = messageId;
		this.statusCode = StatusCodeEnum.OK;
		this.reason = null;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public String getReason() {
		return this.reason;
	}

	public StatusCodeEnum getStatusCode() {
		return this.statusCode;
	}

}
