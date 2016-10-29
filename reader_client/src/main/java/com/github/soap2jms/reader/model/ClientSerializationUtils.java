package com.github.soap2jms.reader.model;

import java.util.List;
import java.util.Map;

import com.github.soap2jms.reader.common.ws.S2JMessage.Headers;

public class ClientSerializationUtils {
	public static Map<String,Object> convertHeaders(List <Headers> headers){
		for(Headers header: headers){
			String key = header.getKey();
			String value = header.getValue();
	
		}
		return null;
	}

}
