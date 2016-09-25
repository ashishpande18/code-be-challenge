package com.iot.codechallenge.service;

import java.util.List;

import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.messages.MetricRequest;

/**
 * This is a interface for MongoDB service
 * Responsible for all MongoDB interaction of application 
 * @author Ashish P. Pande
 * @version 1.0.0
 */
public interface MongoDBService {
	
	/**
	 * This method saves Mertic object to database
	 * @param metric
	 * @return Metric
	 */
	public Metric saveMetric(MetricRequest request);
	
	/**
	 * This method returns all available Metric records in database
	 * @return List<Metric>
	 */
	public List<Metric> readMetric();
	
	/**
	 * This method returns all the Metric records matching the 
	 * criteria of start and end timestamp range
	 * @param startRange
	 * @param endRange
	 * @return List<Metric>
	 */
	public List<Metric> readByTimeRange(long startRange, long endRange);
	
	/**
	 * This method is responsible for evaluating metric for Alert 
	 * scenario and create alert if rule condition satisfies.
	 * @param metric
	 * @return boolean
	 */
	public boolean isQualifyForAlert(Metric metric);
	
	/**
	 * This method creates new Alert in database
	 * @param alert
	 * @return Alert
	 */
	public Alert createAlert(Alert alert);
	
	/**
	 * This method returns all available Alert records in database
	 * @return List<Alert>
	 */
	public List<Alert> readAlert();
	
	/**
	 * This method returns all the Alert records matching the 
	 * criteria of start and end timestamp range 
	 * @param startRange
	 * @param endRange
	 * @return List<Alert>
	 */
	public List<Alert> readAlertsByTimeRange(long startRange, long endRange);
	
}
