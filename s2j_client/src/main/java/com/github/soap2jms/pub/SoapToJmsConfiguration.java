package com.github.soap2jms.pub;

/**
 * This class is used to provide configuration to SoapToJmsClient.
 * 
 * @author g.Contini
 * @see SoapToJmsClient#SoapToJmsClient(SoapToJmsConfiguration)
 */
public class SoapToJmsConfiguration {

	private final Integer connectionTimeout;
	private final String contextUrl;
	private final Integer requestTimeout;

	public SoapToJmsConfiguration(final String contextbase) {
		this.connectionTimeout = null;
		this.requestTimeout = null;
		this.contextUrl = contextbase;
	}

	/**
	 * Constructor that allows to specify all the configuration parameters at once.
	 * 
	 * @param contextUrl
	 *            the complete url of the web application where the server is
	 *            deployed.
	 * @param connectionTimeout
	 *            Connection timeout in milliseconds. If null default timeout
	 *            will be used.
	 * @param requestTimeout
	 *            Read timeout in milliseconds. If null default timeout
	 *            will be used.
	 */
	public SoapToJmsConfiguration(final String contextUrl, final Integer connectionTimeout,
			final Integer requestTimeout) {
		this.connectionTimeout = connectionTimeout;
		this.requestTimeout = requestTimeout;
		this.contextUrl = contextUrl;
	}

	public Integer getConnectionTimeout() {
		return this.connectionTimeout;
	}

	public String getContextUrl() {
		return this.contextUrl;
	}

	public Integer getRequestTimeout() {
		return this.requestTimeout;
	}

}
