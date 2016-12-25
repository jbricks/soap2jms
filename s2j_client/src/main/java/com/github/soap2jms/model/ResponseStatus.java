package com.github.soap2jms.model;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.ws.MessageIdAndStatus;

public class ResponseStatus {
	public static class MessageStatus {
		private final S2JMessage message;
		private final MessageDeliveryStatus deliveryStatus;
		private final StatusCodeEnum statusCode;
		private final String reason;

		public MessageStatus(S2JMessage message, MessageDeliveryStatus deliveryStatus, StatusCodeEnum statusCode,
				String reason) {
			this.message = message;
			this.deliveryStatus = deliveryStatus;
			this.statusCode = statusCode;
			this.reason = reason;
		}

		public S2JMessage getMessage() {
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
				try {
					result = "MessageStatus [message.clientId=" + message.getClientId() + ", message.jmsId="
							+ message.getJMSMessageID() + ", statusCode=OK";
				} catch (Exception e) {
					result = "MessageStatus [message=" + message + ", statusCode=OK";
				}
			} else {
				result = "MessageStatus [message=" + message + ", deliveryStatus=" + deliveryStatus + ", statusCode="
						+ statusCode + ", reason=" + reason + "]";
			}
			return result;
		}

	}

	private final List<MessageStatus> messageStatus = new ArrayList<MessageStatus>();
	private final List<MessageStatus> delivered = new ArrayList<MessageStatus>();
	private final List<MessageStatus> failed = new ArrayList<MessageStatus>();
	private final List<MessageStatus> inDoubt = new ArrayList<MessageStatus>();



	
	private int errorCount;
	private int warnCount;
	private int deliveredCount;

	public ResponseStatus() {
	}

	public void addMessage(S2JMessage message, MessageDeliveryStatus deliveryStatus, StatusCodeEnum statusCode,
			String reason) {
		final MessageStatus messageStatus = new MessageStatus(message, deliveryStatus, statusCode, reason);
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

	public MessageStatus[] getAllMessageSstatus() {
		return (MessageStatus[]) messageStatus.toArray(new MessageStatus[messageStatus.size()]);
	}

	public MessageStatus[] getDelivered() {
		return (MessageStatus[]) delivered.toArray(new MessageStatus[delivered.size()]);
	}
	
	public MessageStatus[] getFailed() {
		return (MessageStatus[]) failed.toArray(new MessageStatus[failed.size()]);
	}

	public MessageStatus[] getinDoubt() {
		return (MessageStatus[]) inDoubt.toArray(new MessageStatus[inDoubt.size()]);
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
