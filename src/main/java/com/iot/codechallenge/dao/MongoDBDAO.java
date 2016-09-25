package com.iot.codechallenge.dao;

import java.util.List;

import org.mongodb.morphia.Key;

import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;

/**
 * This is a interface for MongoDB DAO
 * Responsible for all MongoDB interaction of application 
 * @author Ashish P. Pande
 * @version 1.0.0
 */
public interface MongoDBDAO {
	
	/**
	 * This method saves Mertic object to database
	 * @param metric
	 * @return Key<Metric>
	 */
	public Key<Metric> saveMetric(Metric metric);
	
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
	public List<Metric> readMetricsByTimeRange(long startRange, long endRange);
	
	/**
	 * This method creates new Alert in database
	 * @param alert
	 * @return Key<Alert>
	 */
	public Key<Alert> createAlert(Alert alert);
	
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
