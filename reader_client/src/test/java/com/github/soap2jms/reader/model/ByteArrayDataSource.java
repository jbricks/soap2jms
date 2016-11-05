package com.github.soap2jms.reader.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource {
	private final byte[] bytes;
	
	
	public ByteArrayDataSource(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String getContentType() {
		return "application";
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

}
