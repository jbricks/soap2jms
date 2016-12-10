package com.github.soap2jms.model;

import java.util.ArrayList;
import java.util.List;

import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.ws.MessageIdAndStatus;

public class ResponseStatus {

	private int errorCount = 0;
	private final MessageIdAndStatus[] messages;
	private int successCount = 0;
	private int warnCount = 0;

	public ResponseStatus(final List<MessageIdAndStatus> messageIds) {
		this.messages = messageIds.toArray(new MessageIdAndStatus[messageIds.size()]);
		for (int position = 0; position < messageIds.size(); position++) {
			final MessageIdAndStatus messsageIdAndStatus = this.messages[position];
			final StatusCodeEnum sce = messsageIdAndStatus.getStatus().getCodeEnum();
			if (sce.isError()) {
				this.errorCount++;
			} else {
				this.successCount++;
				if (sce.isWarining()) {
					this.warnCount++;
				}
			}
		}
	}

	public int getErrorCount() {
		return this.errorCount;
	}

	public MessageIdAndStatus[] getFailedMessages() {
		final List<MessageIdAndStatus> failedMsg = new ArrayList<>();
		for (final MessageIdAndStatus messageIdAndStat : this.messages) {
			final StatusCodeEnum sce = messageIdAndStat.getStatus().getCodeEnum();
			if (sce.isError()) {
				failedMsg.add(messageIdAndStat);
			}
		}
		return failedMsg.toArray(new MessageIdAndStatus[failedMsg.size()]);
	}

	public int getSuccessCount() {
		return this.successCount;
	}

	public int getTotalCount() {
		return this.messages.length;
	}

	public int getWarnCount() {
		return this.warnCount;
	}
}
