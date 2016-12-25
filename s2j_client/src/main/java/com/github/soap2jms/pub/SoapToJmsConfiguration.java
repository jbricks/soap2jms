package com.github.soap2jms.pub;

public class SoapToJmsConfiguration {
	
	private final String contextBase;
	private final Integer connectionTimeout;
	private final Integer requestTimeout;
	
	public SoapToJmsConfiguration(String contextbase) {
		connectionTimeout = null;
		requestTimeout = null;
		this.contextBase = contextbase;
	}
	
	public SoapToJmsConfiguration(String contextbase, Integer connectionTimeout, Integer requestTimeout) {
		this.connectionTimeout = connectionTimeout;
		this.requestTimeout = requestTimeout;
		this.contextBase = contextbase;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	public String getContextBase() {
		return contextBase;
	}
	
	

}
