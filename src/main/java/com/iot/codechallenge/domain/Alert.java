package com.iot.codechallenge.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * This is Entity class representing Alert record in MongoDB
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@Entity ("Alerts")
public class Alert {
	
	@Id
	private ObjectId Id;
	
	private String alertType;
	
	private float percentageChange;
	
	private long timestamp;
	
	private ObjectId metricId;

	public ObjectId getId() {
		return Id;
	}

	public void setId(ObjectId id) {
		Id = id;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public float getPercentageChange() {
		return percentageChange;
	}

	public void setPercentageChange(float percentageChange) {
		this.percentageChange = percentageChange;
	}

	public ObjectId getMetricId() {
		return metricId;
	}

	public void setMetricId(ObjectId metricId) {
		this.metricId = metricId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alert other = (Alert) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
}
