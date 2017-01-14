package com.github.soap2jms.common;

import javax.jms.Message;

@Deprecated
public class MessageAndStatus {
	private final String jmsCode;
	private final Message message;
	private final String reason;
	private final StatusCodeEnum sce;

	public MessageAndStatus(final Message message, final StatusCodeEnum sce, final String reason,
			final String jmsCode) {
		this.message = message;
		this.sce = sce;
		this.reason = reason;
		this.jmsCode = jmsCode;
	}

	public String getJmsCode() {
		return this.jmsCode;
	}

	public Message getMessage() {
		return this.message;
	}

	public String getReason() {
		return this.reason;
	}

	public StatusCodeEnum getSce() {
		return this.sce;
	}

}
