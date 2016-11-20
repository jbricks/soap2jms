package com.github.soap2jms.queue;

import com.github.soap2jms.common.StatusCodeEnum;

public class IdAndStatus {

	private final StatusCodeEnum statusCode;
	private final String messageId;
	private final String reason;

	public IdAndStatus(StatusCodeEnum statusCode, String messageId, String reason) {
		this.statusCode = statusCode;
		this.messageId = messageId;
		this.reason = reason;
	}

	public IdAndStatus(String messageId) {
		this.messageId = messageId;
		this.statusCode = StatusCodeEnum.OK;
		this.reason = null;
	}

	public StatusCodeEnum getStatusCode() {
		return statusCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getReason() {
		return reason;
	}

}
