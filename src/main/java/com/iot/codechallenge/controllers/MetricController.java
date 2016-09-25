package com.iot.codechallenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.messages.DefaultResponse;
import com.iot.codechallenge.messages.MetricRequest;
import com.iot.codechallenge.service.MongoDBService;
import com.mongodb.MongoException;

/**
 * This controller class is responsible for handling 
 * Metric create requests
 * @author Ashish P. Pande
 * @version 1.0.0
 * 
 */
@RestController
public class MetricController {
	
	@Autowired
	private MongoDBService mongoDBService;

	/**
	 * This request handler method for create metric request.
	 * @param MetricRequest
	 * @return DefaultResponse
	 */
	@PostMapping("/create/metric")
	@ResponseBody
	public DefaultResponse createMetric(@RequestBody MetricRequest request){
		
		
		Metric metric = mongoDBService.saveMetric(request);
		if(metric != null && metric.getValue() > 0 && metric.getMetricId() != null){
			mongoDBService.isQualifyForAlert(metric);
		}
		System.out.println(" TimeStamp:"+request.getTimeStamp()+" Value:"+request.getValue());
		return new DefaultResponse(DefaultResponse.RESPONSE_TYPE_SUCCESS, "");
		
	}
	
	/**
	 * This is exception handler method for all exceptions.
	 * @param exception
	 * @return DefaultResponse 
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public DefaultResponse handleException(MongoException exception) {
		DefaultResponse response = new DefaultResponse(DefaultResponse.RESPONSE_TYPE_ERROR, exception.getMessage());
		return response;
	  }
	
	
	public MongoDBService getMongoDBService() {
		return mongoDBService;
	}

	public void setMongoDBService(MongoDBService mongoDBService) {
		this.mongoDBService = mongoDBService;
	}

}
