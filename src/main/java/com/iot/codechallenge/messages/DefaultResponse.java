package com.iot.codechallenge.messages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is Default response representation of JSON response
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@XmlRootElement
public class DefaultResponse {
	
	public static String RESPONSE_TYPE_SUCCESS = "SUCCESS";
	public static String RESPONSE_TYPE_ERROR = "ERROR";
	
	public DefaultResponse(){
		
	}
	
	public DefaultResponse(String responseType, String responseDesc){
		setResponseType(responseType);
		setResponseDesc(responseDesc);
	}
	
	private String responseType;
	private String responseDesc;
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

}
