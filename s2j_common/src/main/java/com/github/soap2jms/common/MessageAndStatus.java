package com.github.soap2jms.common;

import javax.jms.Message;

@Deprecated
public class MessageAndStatus {
	private final Message message;
	private final StatusCodeEnum sce;
	private final String reason;
	private final String jmsCode;
	
	
	public MessageAndStatus(Message message, StatusCodeEnum sce, String reason, String jmsCode) {
		this.message = message;
		this.sce = sce;
		this.reason = reason;
		this.jmsCode = jmsCode;
	}
	
	public Message getMessage() {
		return message;
	}
	public StatusCodeEnum getSce() {
		return sce;
	}
	public String getReason() {
		return reason;
	}
	public String getJmsCode() {
		return jmsCode;
	}
	
	
}
