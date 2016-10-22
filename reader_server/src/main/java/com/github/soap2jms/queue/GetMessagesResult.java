package com.github.soap2jms.queue;

import javax.jms.Message;

public class GetMessagesResult {
	public GetMessagesResult(Message[] result, boolean moreMessages) {
		this.result = result;
		this.moreMessages = moreMessages;
	}

	public final Message[] result;
	public final boolean moreMessages;
}