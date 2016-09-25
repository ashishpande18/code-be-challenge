package com.iot.codechallenge.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.codechallenge.dao.MongoDBDAO;
import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.messages.MetricRequest;
import com.iot.codechallenge.rules.util.AlertUtility;
import com.iot.codechallenge.service.MongoDBService;

/**
 * This is service implementation for all the MongoDB 
 * interaction of application
 * @author Ashish P. Pande
 * @version 1.0,0
 */
@Service
public class MongoDBServiceImpl implements MongoDBService {
	
	@Autowired
	MongoDBDAO mongoDBDAO;
	
	@Autowired
	AlertUtility alertUtility;

	/**
	 * This method saves Mertic object to database
	 * @param metric
	 * @return Metric
	 */
	@Override
	public Metric saveMetric(MetricRequest request) {
		Metric metric = convertRequestToDomainObject(request);
		if(metric != null){
			Key<Metric> metricKey = mongoDBDAO.saveMetric(metric);
			metric.setMetricId((ObjectId)metricKey.getId());
			return metric;
		}else{
			return null;
		}		
	}

	/**
	 * This method returns all available Metric records in database
	 * @return List<Metric>
	 */
	@Override
	public List<Metric> readMetric() {
		return mongoDBDAO.readMetric();
	}

	/**
	 * This method returns all the Metric records matching the 
	 * criteria of start and end timestamp range
	 * @param startRange
	 * @param endRange
	 * @return List<Metric>
	 */
	@Override
	public List<Metric> readByTimeRange(long startRange, long endRange) {
		return mongoDBDAO.readMetricsByTimeRange(startRange, endRange);
	}

	/**
	 * This is helper method to convert request object to Metric Entity
	 * @param MetricRequest
	 * @return Metric
	 */
	protected Metric convertRequestToDomainObject(MetricRequest request) {
		Metric metric = null; 
		if(request != null){
			metric = new Metric();
				if(request.getTimeStamp() > 0){
					metric.setTimestamp(request.getTimeStamp());
				}
				
				if(request.getValue() > 0){
					metric.setValue(request.getValue());
				}
			}
			
		return metric;
	}
	
	/**
	 * This method creates new Alert in database
	 * @param alert
	 * @return Alert
	 */
	@Override
	public Alert createAlert(Alert alert) {
		Key<Alert> alertKey = mongoDBDAO.createAlert(alert);
		alert.setId((ObjectId)alertKey.getId());
		return alert;
	}

	/**
	 * This method returns all available Alert records in database
	 * @return List<Alert>
	 */
	@Override
	public List<Alert> readAlert() {
		return mongoDBDAO.readAlert();
	}

	/**
	 * This method returns all the Alert records matching the 
	 * criteria of start and end timestamp range 
	 * @param startRange
	 * @param endRange
	 * @return List<Alert>
	 */
	@Override
	public List<Alert> readAlertsByTimeRange(long startRange, long endRange) {
		return mongoDBDAO.readAlertsByTimeRange(startRange, endRange);
	}

	/**
	 * This method is responsible for evaluating metric for Alert 
	 * scenario and create alert if rule condition satisfies.
	 * @param metric
	 * @return boolean
	 */
	@Override
	public boolean isQualifyForAlert(Metric metric) {
		
		return alertUtility.isQualifyForAlert(metric);
	}

}
