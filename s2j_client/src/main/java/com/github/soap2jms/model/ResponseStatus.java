package com.github.soap2jms.model;

import java.util.ArrayList;
import java.util.List;

import com.github.soap2jms.common.StatusCodeEnum;

public class ResponseStatus<T> {
	public static class MessageStatus<T> {
		private final MessageDeliveryStatus deliveryStatus;
		private final T message;
		private final String reason;
		private final StatusCodeEnum statusCode;

		public MessageStatus(final T message, final MessageDeliveryStatus deliveryStatus,
				final StatusCodeEnum statusCode, final String reason) {
			this.message = message;
			this.deliveryStatus = deliveryStatus;
			this.statusCode = statusCode;
			this.reason = reason;
		}

		public MessageDeliveryStatus getDeliveryStatus() {
			return this.deliveryStatus;
		}

		public T getMessage() {
			return this.message;
		}

		public String getReason() {
			return this.reason;
		}

		public StatusCodeEnum getStatusCode() {
			return this.statusCode;
		}

		@Override
		public String toString() {
			String result;
			if (this.statusCode == StatusCodeEnum.OK) {
				result = "MessageStatus [message=" + this.message + ", statusCode=OK";
			} else {
				result = "MessageStatus [message=" + this.message + ", deliveryStatus=" + this.deliveryStatus
						+ ", statusCode=" + this.statusCode + ", reason=" + this.reason + "]";
			}
			return result;
		}

	}

	private final List<MessageStatus<T>> delivered = new ArrayList<>();
	private int deliveredCount;
	private int errorCount;
	private final List<MessageStatus<T>> failed = new ArrayList<>();

	private final List<MessageStatus<T>> inDoubt = new ArrayList<>();
	private final List<MessageStatus<T>> messageStatus = new ArrayList<>();
	private int warnCount;

	public ResponseStatus() {
	}

	public void addMessage(final T message, final MessageDeliveryStatus deliveryStatus, final StatusCodeEnum statusCode,
			final String reason) {
		final MessageStatus<T> messageStatus = new MessageStatus<>(message, deliveryStatus, statusCode, reason);
		this.messageStatus.add(messageStatus);
		switch (deliveryStatus) {
		case DELIVERED:
			if (statusCode == StatusCodeEnum.OK) {
				this.deliveredCount++;
				this.delivered.add(messageStatus);
			} else if (statusCode.isWarining()) {
				this.deliveredCount++;
				this.warnCount++;
				this.delivered.add(messageStatus);
			} else {
				this.errorCount++;
				this.failed.add(messageStatus);
			}
			break;
		case IN_DOUBT:
			this.inDoubt.add(messageStatus);
			this.errorCount++;
			break;
		case NOT_DELIVERED:
			this.failed.add(messageStatus);
			this.errorCount++;
			break;
		default:
			throw new UnsupportedOperationException("this isn't supposed to happen");
		}
	}

	@SuppressWarnings("unchecked")
	public MessageStatus<T>[] getAllMessageSstatus() {
		return this.messageStatus.toArray(new MessageStatus[this.messageStatus.size()]);
	}

	@SuppressWarnings("unchecked")
	public MessageStatus<T>[] getDelivered() {
		return this.delivered.toArray(new MessageStatus[this.delivered.size()]);
	}

	public int getErrorCount() {
		return this.errorCount;
	}

	@SuppressWarnings("unchecked")
	public MessageStatus<T>[] getFailed() {
		return this.failed.toArray(new MessageStatus[this.failed.size()]);
	}

	@SuppressWarnings("unchecked")
	public MessageStatus<T>[] getinDoubt() {
		return this.inDoubt.toArray(new MessageStatus[this.inDoubt.size()]);
	}

	public int getSuccessCount() {
		return this.deliveredCount;
	}

	public int getTotalCount() {
		return this.messageStatus.size();
	}

	public int getWarnCount() {
		return this.warnCount;
	}
}
