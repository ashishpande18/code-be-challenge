package com.iot.codechallenge.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 * This is Entity class representing Metric record in MongoDB
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@Entity("Metrics")
@Indexes(
			@Index(fields = @Field("timeStamp"))
		)
public class Metric {
	
	@Id
	private ObjectId metricId;

	private long timestamp;
	private int value;
	
	
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metricId == null) ? 0 : metricId.hashCode());
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
		Metric other = (Metric) obj;
		if (metricId == null) {
			if (other.metricId != null)
				return false;
		} else if (!metricId.equals(other.metricId))
			return false;
		return true;
	}
	
	
	
}
