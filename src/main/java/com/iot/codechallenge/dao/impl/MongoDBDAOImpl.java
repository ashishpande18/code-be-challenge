package com.iot.codechallenge.dao.impl;

import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.iot.codechallenge.dao.MongoDBDAO;
import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;
import com.mongodb.MongoClient;

/**
 * This class is implementation of all the MongoDB 
 * interaction of application
 * @author Ashish P. Pande
 * @version 1.0,0
 */
@Repository
@PropertySource("classpath:mongoDBConfig.properties")
public class MongoDBDAOImpl implements MongoDBDAO{
	
	@Value("${dataBaseName}")
	private String dbName;
	
	@Value("${mapingPackage}")
	private String packageNameList;
	
	private Morphia morphiaInstance;
	
	private Datastore datastore;
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getPackageNameList() {
		return this.packageNameList;
	}

	public void setPackageNameList(String packageNameList) {
		this.packageNameList = packageNameList;
	}
	
	public Morphia getMorphiaInstance() {
		return morphiaInstance;
	}

	public void setMorphiaInstance(Morphia morphiaInstance) {
		this.morphiaInstance = morphiaInstance;
	}

	public MongoDBDAOImpl(){		
		
	}
	
	/**
	 * Init method for DAO to prepare DB connectivity 
	 * before serving any DB call
	 */
	@PostConstruct
	public void initEnv(){
		morphiaInstance  = new Morphia();
		StringTokenizer stringTokenizer = new StringTokenizer(getPackageNameList(), ",");
		while(stringTokenizer.hasMoreTokens()){
			morphiaInstance.mapPackage(stringTokenizer.nextToken());
		}
		
		datastore = getMorphiaInstance().createDatastore(new MongoClient(), getDbName());
	}
	
	/**
	 * This method saves Mertic object to database
	 * @param metric
	 * @return Key<Metric>
	 */
	@Override
	public Key<Metric> saveMetric(Metric metric) {
		
		return datastore.save(metric);
	}
	
	/**
	 * This method returns all available Metric records in database
	 * @return List<Metric>
	 */
	@Override
	public List<Metric> readMetric() {
		Query<Metric> query = datastore.createQuery(Metric.class);
		return query.asList();
	}

	/**
	 * This method returns all the Metric records matching the 
	 * criteria of start and end timestamp range
	 * @param startRange
	 * @param endRange
	 * @return List<Metric>
	 */
	@Override
	public List<Metric> readMetricsByTimeRange(long startRange, long endRange) {
        final Query<Metric> query = datastore.createQuery(Metric.class);
        query.criteria("timestamp").lessThanOrEq(endRange);
        query.criteria("timestamp").greaterThanOrEq(startRange);
        return query.asList();
	}

	/**
	 * This method creates new Alert in database
	 * @param alert
	 * @return Key<Alert>
	 */
	@Override
	public Key<Alert>  createAlert(Alert alert) {
		return datastore.save(alert);
	}

	/**
	 * This method returns all available Alert records in database
	 * @return List<Alert>
	 */
	@Override
	public List<Alert> readAlert() {
		Query<Alert> query = datastore.createQuery(Alert.class);
		return query.asList();
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
		final Query<Alert> query = datastore.createQuery(Alert.class);
        query.criteria("timestamp").lessThanOrEq(endRange);
        query.criteria("timestamp").greaterThanOrEq(startRange);
        return query.asList();
	}

}
