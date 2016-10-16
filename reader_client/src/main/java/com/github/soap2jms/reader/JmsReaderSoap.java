package com.github.soap2jms.reader;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "jmsReaderSoap", wsdlLocation = "file:/usr/local/prj/misc/soap2jms/reader_common/src/main/resources/queueReader.wsdl", targetNamespace = "http://soap2jms.github.com/reader")
public class JmsReaderSoap extends Service {

	public final static QName SERVICE = new QName("http://soap2jms.github.com/reader", "jmsReaderSoap");
	public final static QName ReaderSOAP = new QName("http://soap2jms.github.com/reader", "readerSOAP");

	public JmsReaderSoap(String wsdlLocation) throws MalformedURLException {
		super(new URL(wsdlLocation), SERVICE);
	}

	public JmsReaderSoap(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public JmsReaderSoap(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public JmsReaderSoap(URL wsdlLocation, WebServiceFeature... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public JmsReaderSoap(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
		super(wsdlLocation, serviceName, features);
	}

	/**
	 *
	 * @return returns ReaderSoap2JmsGithubCom
	 */
	@WebEndpoint(name = "readerSOAP")
	public ReaderSoap2JmsGithubCom getReaderSOAP() {
		return super.getPort(ReaderSOAP, ReaderSoap2JmsGithubCom.class);
	}

	/**
	 * 
	 * @param features
	 *            A list of {@link javax.xml.ws.WebServiceFeature} to configure
	 *            on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns ReaderSoap2JmsGithubCom
	 */
	@WebEndpoint(name = "readerSOAP")
	public ReaderSoap2JmsGithubCom getReaderSOAP(WebServiceFeature... features) {
		return super.getPort(ReaderSOAP, ReaderSoap2JmsGithubCom.class, features);
	}

}
