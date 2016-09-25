package com.iot.codechallenge.messages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is representation of timestamp range request JSON 
 * request for Metric and Alert search
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@XmlRootElement
public class RangeRequest {	
 
		private long startRange;
		private long endRange;
		
		public long getStartRange() {
			return startRange;
		}
		public void setStartRange(long startRange) {
			this.startRange = startRange;
		}
		public long getEndRange() {
			return endRange;
		}
		public void setEndRange(long endRange) {
			this.endRange = endRange;
		}
		
		
		
		

}
