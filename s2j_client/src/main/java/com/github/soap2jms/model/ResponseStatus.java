package com.github.soap2jms.model;

import java.util.ArrayList;
import java.util.List;

import com.github.soap2jms.common.StatusCodeEnum;

public class ResponseStatus<T> {
	public static class MessageStatus<T> {
		private final T message;
		private final MessageDeliveryStatus deliveryStatus;
		private final StatusCodeEnum statusCode;
		private final String reason;

		public MessageStatus(T message, MessageDeliveryStatus deliveryStatus, StatusCodeEnum statusCode,
				String reason) {
			this.message = message;
			this.deliveryStatus = deliveryStatus;
			this.statusCode = statusCode;
			this.reason = reason;
		}

		public T getMessage() {
			return message;
		}

		public MessageDeliveryStatus getDeliveryStatus() {
			return deliveryStatus;
		}

		public StatusCodeEnum getStatusCode() {
			return statusCode;
		}

		public String getReason() {
			return reason;
		}

		@Override
		public String toString() {
			String result;
			if (statusCode == StatusCodeEnum.OK) {
				result = "MessageStatus [message=" + message + ", statusCode=OK";
			} else {
				result = "MessageStatus [message=" + message + ", deliveryStatus=" + deliveryStatus + ", statusCode="
						+ statusCode + ", reason=" + reason + "]";
			}
			return result;
		}

	}

	private final List<MessageStatus<T>> messageStatus = new ArrayList<MessageStatus<T>>();
	private final List<MessageStatus<T>> delivered = new ArrayList<MessageStatus<T>>();
	private final List<MessageStatus<T>> failed = new ArrayList<MessageStatus<T>>();
	private final List<MessageStatus<T>> inDoubt = new ArrayList<MessageStatus<T>>();



	
	private int errorCount;
	private int warnCount;
	private int deliveredCount;

	public ResponseStatus() {
	}

	public void addMessage(T message, MessageDeliveryStatus deliveryStatus, StatusCodeEnum statusCode,
			String reason) {
		final MessageStatus<T> messageStatus = new MessageStatus<T>(message, deliveryStatus, statusCode, reason);
		this.messageStatus.add(messageStatus);
		switch ( deliveryStatus) {
		case DELIVERED:
			if (statusCode == StatusCodeEnum.OK) {
				deliveredCount++;
				this.delivered.add(messageStatus);
			} else if (statusCode.isWarining()) {
				deliveredCount++;
				warnCount++;
				this.delivered.add(messageStatus);
			} else {
				errorCount++;
				this.failed.add(messageStatus);
			}
		break;
		case UNKNOWN:
			this.inDoubt.add(messageStatus);
			errorCount++;
			break;
		case NOT_DELIVERED:
			this.failed.add(messageStatus);
			errorCount++;
			break;
		default:
			throw new UnsupportedOperationException("this isn't supposed to happen");
		}
	}

	public int getErrorCount() {
		return this.errorCount;
	}

	public MessageStatus<T>[] getAllMessageSstatus() {
		return (MessageStatus[]) messageStatus.toArray(new MessageStatus[messageStatus.size()]);
	}

	public MessageStatus<T>[] getDelivered() {
		return (MessageStatus<T>[]) delivered.toArray(new MessageStatus[delivered.size()]);
	}
	
	public MessageStatus<T>[] getFailed() {
		return (MessageStatus<T>[]) failed.toArray(new MessageStatus[failed.size()]);
	}

	public MessageStatus<T>[] getinDoubt() {
		return (MessageStatus<T>[]) inDoubt.toArray(new MessageStatus[inDoubt.size()]);
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
