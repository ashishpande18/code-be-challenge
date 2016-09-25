
package com.iot.codechallenge.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.messages.DefaultResponse;
import com.iot.codechallenge.messages.RangeRequest;
import com.iot.codechallenge.service.MongoDBService;

/**
 * This controller class is responsible for handling all search 
 * requests for Alert as well as Metric 
 * @author Ashish P. Pande
 * @version 1.0.0
 * 
 */
@RestController
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private MongoDBService mongoDBService;
	
	/**
	 * This is request handler method for search Metric 
	 * request without any parameter and returns list 
	 * of all available alerts in the mongoDB Metric collections 
	 * @param
	 * @return List<Metric>
	 */
	@GetMapping("/read/metrics")
	@ResponseBody
	public List<Metric> readMetrics(){
		return mongoDBService.readMetric();		
	}
	
	/**
	 * This is request handler method for search Alert 
	 * request without any parameter and returns list 
	 * of all available alerts in the mongoDB Alert collections
	 * @param 
	 * @return List<Alert>
	 */
	@GetMapping("/read/alerts")
	@ResponseBody
	public List<Alert> readAlerts(){
		return mongoDBService.readAlert();
	}
	
	
	/**
	 * This is request handler method for search Metric 
	 * request with range parameters as post body
	 * @param range
	 * @return List<Metric>
	 */
	@PostMapping("/read/metrics")
	@ResponseBody
	public List<Metric> readMetricsByTimeRange(@RequestBody RangeRequest range){
		
		if(range != null && range.getStartRange() > 0 && range.getEndRange() > 0){
			return mongoDBService.readByTimeRange(range.getStartRange(), range.getEndRange());
		}else{
			logger.warn("SearchController: readMetricsByTimeRange: Invalid Input Parameters: Start Range: "+range.getStartRange()+" End Range: "+range.getEndRange());
			return new ArrayList<Metric>();
		}		
	}
	
	/**
	 * This is request handler method for search Alert 
	 * request with range parameters as post body
	 * @param range
	 * @return List<Alert>
	 */
	@PostMapping("/read/alerts")
	@ResponseBody
	public List<Alert> readAlertsByTimeRange(@RequestBody RangeRequest range){
		
		if(range != null && range.getStartRange() > 0 && range.getEndRange() > 0){
			return mongoDBService.readAlertsByTimeRange(range.getStartRange(), range.getEndRange());
		}else{
			logger.warn("SearchController: readMetricsByTimeRange: Invalid Input Parameters: Start Range: "+range.getStartRange()+" End Range: "+range.getEndRange());
			return new ArrayList<Alert>();
		}		
	}
	
	/**
	 * This method is a exception handler method.
	 * Responsible for generating all error scenario responses
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public DefaultResponse handleException(Exception exception) {
		DefaultResponse response = new DefaultResponse(DefaultResponse.RESPONSE_TYPE_ERROR, exception.getMessage());
		logger.error(exception.getMessage(), exception);
		return response;
	  }	

	public MongoDBService getMongoDBService() {
		return mongoDBService;
	}

	public void setMongoDBService(MongoDBService mongoDBService) {
		this.mongoDBService = mongoDBService;
	}
	
}
