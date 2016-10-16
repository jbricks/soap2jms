
package com.github.soap2jms.reader;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-10-10T10:46:05.413+08:00
 * Generated source version: 3.1.7
 * 
 */
public final class ReaderSoap2JmsGithubCom_ReaderSOAP_Client {

    private static final QName SERVICE_NAME = new QName("http://soap2jms.github.com/reader", "jmsReaderSoap");

    private ReaderSoap2JmsGithubCom_ReaderSOAP_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = null;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        JmsReaderSoap ss = new JmsReaderSoap(wsdlURL, SERVICE_NAME);
        ReaderSoap2JmsGithubCom port = ss.getReaderSOAP();  
        
        {
        System.out.println("Invoking retrieveMessages...");
        java.lang.String _retrieveMessages_filter = "_retrieveMessages_filter-1902238976";
        int _retrieveMessages_maxItems = 1548387551;
        com.github.soap2jms.reader_common.RetrieveMessageResponseType _retrieveMessages__return = port.retrieveMessages(
        		"", _retrieveMessages_filter, _retrieveMessages_maxItems);
        System.out.println("retrieveMessages.result=" + _retrieveMessages__return);


        }
        {
        System.out.println("Invoking acknowledgeMessages...");
        java.util.List<java.lang.String> _acknowledgeMessages_messageId = new java.util.ArrayList<java.lang.String>();
        java.lang.String _acknowledgeMessages_messageIdVal1 = "_acknowledgeMessages_messageIdVal210798123";
        _acknowledgeMessages_messageId.add(_acknowledgeMessages_messageIdVal1);
        java.util.List<com.github.soap2jms.reader_common.MessageIdAndStatus> _acknowledgeMessages__return = port.acknowledgeMessages(_acknowledgeMessages_messageId);
        System.out.println("acknowledgeMessages.result=" + _acknowledgeMessages__return);


        }
        System.exit(0);
    }

}
