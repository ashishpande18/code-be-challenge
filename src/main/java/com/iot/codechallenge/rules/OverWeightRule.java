package com.iot.codechallenge.rules;

import org.easyrules.core.BasicRule;

import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.service.MongoDBService;

/**
 * This is Rule implementation of over weight rule, defines 
 * over weight condition and execution
 * @author Ashish P. Pande
 * @version 1.0.0
 */
public class OverWeightRule extends BasicRule {
	private Metric metric;
	private float percentChange;
	private static final String ALERT_TYPE = "OVERWEIGHT_ALERT";
	
	protected MongoDBService mongoDBService;

	protected int baseWeight;
	


	public OverWeightRule() {

	}

	public OverWeightRule(final String name, final String description, final int priority, final Metric metric) {
		super(name, description, priority);
		this.metric = metric;
	}

	/**
	 * This method evaluates the rule over weight condition
	 * @return boolean
	 */
	@Override
	public boolean evaluate() {

		percentChange = ((metric.getValue() - getBaseWeight()) / (getBaseWeight() * 1.0f)) * 100;
		System.out.println("Overweight Rule: percentChange: " + percentChange);
		if (percentChange > 10) {
			return true;
		}

		return false;
	}

	/**
	 * This method implements the action of rule. This gets executed, 
	 * if over weight rule conditions get satisfied
	 */
	@Override
	public void execute() throws Exception {
		Alert underWeightAlert = new Alert();
		underWeightAlert.setAlertType(ALERT_TYPE);
		underWeightAlert.setPercentageChange(percentChange);
		underWeightAlert.setMetricId(metric.getMetricId());
		underWeightAlert.setTimestamp(metric.getTimestamp());
		mongoDBService.createAlert(underWeightAlert);
	}
	
	
	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	
	public int getBaseWeight() {
		return baseWeight;
	}

	public void setBaseWeight(int baseWeight) {
		this.baseWeight = baseWeight;
	}

	public MongoDBService getMongoDBService() {
		return mongoDBService;
	}

	public void setMongoDBService(MongoDBService mongoDBService) {
		this.mongoDBService = mongoDBService;
	}

}
