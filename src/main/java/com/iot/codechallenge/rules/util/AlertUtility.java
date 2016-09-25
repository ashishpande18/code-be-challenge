package com.iot.codechallenge.rules.util;

import java.util.Stack;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.rules.OverWeightRule;
import com.iot.codechallenge.rules.UnderWeightRule;
import com.iot.codechallenge.service.MongoDBService;

/**
 * This class is a utility class for handling Alert scenarios. 
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@Component
@PropertySource("classpath:baseConfig.properties")
public class AlertUtility {

	private Stack<RulesEngine> rulesEnginePool = new Stack<>();
	
	private Object semaphore = new Object();

	@Autowired
	private MongoDBService mongoDBService;
	
	@Value("${baseWeight}")
	protected int baseWeight;
	
	private static final String UNDER_WEIGHT_RULE_NAME = "Under Weight Rule";
	private static final String UNDER_WEIGHT_RULE_DESC = "Under Weight Rule Description";
	private static final String OVER_WEIGHT_RULE_NAME = "Over Weight Rule";
	private static final String OVER_WEIGHT_RULE_DESC = "Over Weight Rule Description";
	private static final int RULE_PRIORITY = 1;
	
	public AlertUtility(){
		RulesEngineBuilder rulesEngineBuilder = RulesEngineBuilder.aNewRulesEngine();
		for(int i= 0; i < 10; i++){
			RulesEngine  rulesEngine  = rulesEngineBuilder.build();
			rulesEnginePool.push(rulesEngine);
		}		
	}
	
	/**
	 * This method checks if current metric qualify for alert condition 
	 * based on pre defined rules
	 * @param metric
	 * @return boolean
	 */
	public boolean isQualifyForAlert(Metric metric){
		RulesEngine rulesEngine = getRuleEngine();
		UnderWeightRule underWeightRule = getUnderWeightRule(metric);
		OverWeightRule overWeightRule = getOverWeightRule(metric);
		rulesEngine.registerRule(underWeightRule);
		rulesEngine.registerRule(overWeightRule);
		rulesEngine.fireRules();
		returnRuleEngine(rulesEngine);
		return true;
	}
	
	/**
	 * This method returns RuleEngine from pool
	 * @return RulesEngine
	 */
	private RulesEngine getRuleEngine() {
		synchronized (semaphore) {
			return rulesEnginePool.pop();
		}		
	}
	
	/**
	 *  This method returns the RulesEngine to the pool after its use.
	 * @param rulesEngine
	 */
	private void returnRuleEngine(RulesEngine rulesEngine) {
		rulesEngine.clearRules();
		synchronized (semaphore) {
			rulesEnginePool.push(rulesEngine);
		}		
	}
	
	/**
	 * This is a builder method for creation and initialization of under weight rule. 
	 * @param metric
	 * @return UnderWeightRule
	 */
	private UnderWeightRule getUnderWeightRule(Metric metric){
		UnderWeightRule underWeightRule = new UnderWeightRule(UNDER_WEIGHT_RULE_NAME, UNDER_WEIGHT_RULE_DESC, RULE_PRIORITY, metric);
		underWeightRule.setMongoDBService(getMongoDBService());
		underWeightRule.setBaseWeight(getBaseWeight());
		return  underWeightRule;
	}
	
	/**
	 * This is a builder method for creation and initialization of over weight rule. 
	 * @param metric
	 * @return OverWeightRule
	 */
	private OverWeightRule getOverWeightRule(Metric metric){
		OverWeightRule overWeightRule =  new OverWeightRule(OVER_WEIGHT_RULE_NAME, OVER_WEIGHT_RULE_DESC, RULE_PRIORITY, metric);
		overWeightRule.setMongoDBService(getMongoDBService());
		overWeightRule.setBaseWeight(getBaseWeight());
		return overWeightRule;
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
