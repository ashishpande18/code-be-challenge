package com.iot.codechallenge.messages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is representation of metric JSON request
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@XmlRootElement
public class MetricRequest { 
	private long timeStamp;
	private int value;
	
	
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	

}
