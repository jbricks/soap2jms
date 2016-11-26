package com.github.soap2jms.model;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.MessageEOFException;
import javax.jms.MessageFormatException;
import javax.jms.MessageNotReadableException;
import javax.jms.MessageNotWriteableException;

import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.ws.WsJmsMessage;

public class S2JAbstractDataMessage extends S2JMessage {

	private boolean readonly;
	protected DataInputStream instream;
	protected IOException initializeBodyException = null;

	public S2JAbstractDataMessage(final String messageId, final DataHandler body) {
		super(JMSMessageClassEnum.BYTE.name(), messageId, body);
		try {
			instream = new DataInputStream(body.getInputStream());
		} catch (IOException e) {
			instream = null;
			initializeBodyException = e;
		}
	}

	public S2JAbstractDataMessage(final String correlationId, final int deliveryMode, long expiration,
			final Map<String, Object> headers, final String messageId, final Integer priority,
			final boolean redelivered, final long timestamp, final String type, final DataHandler body) {
		super(correlationId, deliveryMode, expiration, headers, messageId, JMSMessageClassEnum.BYTE.name(), priority,
				redelivered, timestamp, type, body);
		try {
			instream = new DataInputStream(body.getInputStream());
		} catch (IOException e) {
			instream = null;
			initializeBodyException = e;
		}
	}

	public S2JAbstractDataMessage(final WsJmsMessage message) {
		super(message);
		try {
			instream = new DataInputStream(message.getBody().getInputStream());
		} catch (IOException e) {
			instream = null;
			initializeBodyException = e;
		}
		this.readonly = true;
	}

	public long getBodyLength() throws JMSException {
		checkBodyReadable();
		int result = 0;

		try {
			result = instream.available();
		} catch (IOException e) {
			handleException("Error in available", e);
		}

		return result;
	}

	private void checkBodyReadable() throws JMSException {
		if (!readonly) {
			throw new MessageNotReadableException("The message " + getJMSMessageID() + " is not in readable state");
		}
		if (initializeBodyException != null) {
			MessageFormatException e1 = new MessageFormatException("Problem reading body for " + getJMSMessageID());
			e1.initCause(initializeBodyException);
			throw e1;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")

	public boolean isBodyAssignableTo(final Class c) throws JMSException {
		final DataHandler messageBody = this.message.getBody();
		try {
			if (messageBody == null || messageBody.getInputStream() == null) {
				return true;
			}
			return Object.class.equals(c) || byte[].class.equals(c);
		} catch (final IOException e) {
			final JMSException e1 = new JMSException("Client error deserializing body", "1");
			e1.initCause(e);
			e1.setLinkedException(e);
			throw e1;
		}
	}

	public boolean readBoolean() throws JMSException {
		checkBodyReadable();
		boolean result = false;
		try {
			result = instream.readBoolean();
		} catch (IOException e) {
			handleException("Error in readBoolean", e);
		}
		return result;
	}

	private void handleException(String cause, IOException e) throws JMSException {
		JMSException e1;
		if (e instanceof EOFException) {
			e1 = new MessageEOFException(cause);
		} else {
			e1 = new MessageFormatException(cause);
		}
		e1.initCause(e);
		e1.setLinkedException(e);
		throw e1;
	}

	public byte readByte() throws JMSException {
		checkBodyReadable();
		byte result = 0;
		try {
			result = instream.readByte();
		} catch (IOException e) {
			handleException("Error in readByte", e);
		}
		return result;
	}

	public int readBytes(final byte[] value) throws JMSException {
		checkBodyReadable();
		int bytes = -1;
		try {
			bytes = instream.read(value);
		} catch (IOException e) {
			handleException("Error in readBytes", e);
		}
		return bytes;
	}

	public int readBytes(final byte[] value, final int length) throws JMSException {
		checkBodyReadable();
		int bytes = -1;
		try {
			bytes = instream.read(value, 0, length);
		} catch (IOException e) {
			handleException("Error in readBytes", e);
		}
		return bytes;
	}

	public char readChar() throws JMSException {
		checkBodyReadable();
		char result = 0;
		try {
			result = instream.readChar();
		} catch (IOException e) {
			handleException("Error in readChar", e);
		}
		return result;
	}

	public double readDouble() throws JMSException {
		checkBodyReadable();
		double result = 0;
		try {
			result = instream.readDouble();
		} catch (IOException e) {
			handleException("Error in readDouble", e);
		}
		return result;
	}

	public float readFloat() throws JMSException {
		checkBodyReadable();
		return 0;
	}

	public int readInt() throws JMSException {
		checkBodyReadable();
		return 0;
	}

	public long readLong() throws JMSException {
		checkBodyReadable();
		return 0;
	}

	public short readShort() throws JMSException {
		checkBodyReadable();
		return 0;
	}

	public int readUnsignedByte() throws JMSException {
		checkBodyReadable();
		return 0;
	}

	public int readUnsignedShort() throws JMSException {
		checkBodyReadable();
		return 0;
	}

	public String readUTF() throws JMSException {
		checkBodyReadable();
		return null;
	}

	public void reset() throws JMSException {
		this.readonly = true;
		if (this.message.getBody() != null) {

		}
	}

	public void writeBoolean(final boolean value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeByte(final byte value) throws JMSException {
		if (this.readonly) {
			throw new IllegalStateException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeBytes(final byte[] value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeBytes(final byte[] value, final int offset, final int length) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeChar(final char value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeDouble(final double value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeFloat(final float value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeInt(final int value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeLong(final long value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeObject(final Object value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeShort(final short value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

	public void writeUTF(final String value) throws JMSException {
		if (this.readonly) {
			throw new MessageNotWriteableException("The message is read only");
		}
		throw new UnsupportedOperationException();

	}

}
