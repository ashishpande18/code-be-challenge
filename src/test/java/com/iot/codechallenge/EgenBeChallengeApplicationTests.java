package com.iot.codechallenge;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.codechallenge.domain.Alert;
import com.iot.codechallenge.domain.Metric;
import com.iot.codechallenge.messages.RangeRequest;
import com.iot.codechallenge.service.impl.MongoDBServiceImpl;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EgenBeChallengeApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private String ALERT_JSON_1 = "{\"alertType\":\"OVERWEIGHT_ALERT\",\"percentageChange\":10.666667,\"timestamp\":1474661988010,\"metricId\":{\"timestamp\":1474661988,\"machineIdentifier\":2706226,\"processIdentifier\":13944,\"counter\":16537410},\"id\":{\"timestamp\":1474661988,\"machineIdentifier\":2706226,\"processIdentifier\":13944,\"counter\":16537411}}";
	private String ALERT_JSON_2 = "{\"alertType\":\"OVERWEIGHT_ALERT\",\"percentageChange\":11.333334,\"timestamp\":1474661993085,\"metricId\":{\"timestamp\":1474661993,\"machineIdentifier\":2706226,\"processIdentifier\":13944,\"counter\":16537412},\"id\":{\"timestamp\":1474661993,\"machineIdentifier\":2706226,\"processIdentifier\":13944,\"counter\":16537413}}";

	private String METRIC_JSON_1 = "{\"metricId\":{\"timestamp\":1474661907,\"machineIdentifier\":2706226,\"processIdentifier\":13944,\"counter\":16537394},\"timestamp\":1474661906521,\"value\":150}";
	private String METRIC_JSON_2 = "{\"metricId\":{\"timestamp\":1474661912,\"machineIdentifier\":2706226,\"processIdentifier\":13944,\"counter\":16537395},\"timestamp\":1474661912269,\"value\":151}";

	private String RANGE_JSON = "{\"startRange\":\"1474661988010\",\"endRange\":\"1474662013215\"}";
	
	 @MockBean
	 private MongoDBServiceImpl mongoDBService;
	 
	 
		@Before
		public void setup() throws JsonParseException, JsonMappingException, IOException {
			ObjectMapper mapper = new ObjectMapper();
			Alert alert1 = mapper.readValue(ALERT_JSON_1, Alert.class);
			Alert alert2 = mapper.readValue(ALERT_JSON_2, Alert.class);
			
			List<Alert> alertList = new ArrayList<Alert>();
			alertList.add(alert1);
			alertList.add(alert2);
			
			given(this.mongoDBService.readAlert())
					.willReturn(alertList);
			
			
			Metric metric1 = mapper.readValue(METRIC_JSON_1, Metric.class);
			Metric metric2 = mapper.readValue(METRIC_JSON_2, Metric.class);
			
			List<Metric> metricList = new ArrayList<Metric>();
			metricList.add(metric1);
			metricList.add(metric2);
			
			given(this.mongoDBService.readMetric())
					.willReturn(metricList);
			
			RangeRequest range  = mapper.readValue(RANGE_JSON, RangeRequest.class);
			
			given(this.mongoDBService.readAlertsByTimeRange(range.getStartRange(), 
															range.getEndRange()))
					.willReturn(alertList);
				
			given(this.mongoDBService.readByTimeRange(range.getStartRange(), 
															range.getEndRange()))
					.willReturn(metricList);
		}

		@Test
		public void testReadAlert() {
			List<Alert> alertList = new ArrayList<Alert>();
			this.restTemplate.getForObject("/read/alerts", alertList.getClass());
		}
		
		@Test
		public void testReadMetric() {
			List<Metric> metricList = new ArrayList<Metric>();
			this.restTemplate.getForObject("/read/metrics", metricList.getClass());
		}
		
		@Test
		public void testReadAlertByRange() throws JsonParseException, JsonMappingException, IOException {
			List<Alert> alertList = new ArrayList<Alert>();
			ObjectMapper mapper = new ObjectMapper();
			RangeRequest range  = mapper.readValue(RANGE_JSON, RangeRequest.class);
			this.restTemplate.postForObject("/read/alerts", range, alertList.getClass());
		}
		
		@Test
		public void testReadMetricRange() throws JsonParseException, JsonMappingException, IOException {
			List<Metric> metricList = new ArrayList<Metric>();
			ObjectMapper mapper = new ObjectMapper();
			RangeRequest range  = mapper.readValue(RANGE_JSON, RangeRequest.class);
			this.restTemplate.postForObject("/read/metrics", range, metricList.getClass());
		}
		 

}
