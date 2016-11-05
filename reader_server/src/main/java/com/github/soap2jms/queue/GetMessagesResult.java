package com.github.soap2jms.queue;

import javax.jms.Message;

public class GetMessagesResult {
	public final boolean moreMessages;

	public final Message[] result;

	public GetMessagesResult(final Message[] result, final boolean moreMessages) {
		this.result = result;
		this.moreMessages = moreMessages;
	}
}