package com.qa.api.mocking.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APImocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APImocksTests extends BaseTest{

	@Test	
	public void getMockUserTest() {
		APImocks.defineGetCallForSingleUser();
		Response response=restClient.get(WIRE_MOCK_BASE_URI, AuthType.NO_AUTH, ContentType.ANY, WIRE_MOCK_ENDPOINT, null, null);
		
	}
	
	@Test	
	public void getMockFileUserTest() {
		APImocks.defineGetCallForSingleUserUsingJsonFile();
		Response response=restClient.get(WIRE_MOCK_BASE_URI, AuthType.NO_AUTH, ContentType.ANY, WIRE_MOCK_ENDPOINT, null, null);
		
	}
	
	@Test	
	public void getMockQueryParamUserTest() {
		APImocks.defineGetCallForSingleUserUsingQueryparam();
		Map<String, String> queryParam=new HashMap<String,String>();
		queryParam.put("name", "girish");
		queryParam.put("status", "inactive");
		Response response=restClient.get(WIRE_MOCK_BASE_URI, AuthType.NO_AUTH, ContentType.ANY, WIRE_MOCK_ENDPOINT, queryParam, null);
		
	}
	
	@Test	
	public void postCallUserTest() {
		APImocks.definePostCallForSingleUser();
		String body="{\r\n"
				+ "        \"name\": \"Ted \",\r\n"
				+ "        \"email\": \"Teddd01@Test.com\",\r\n"
				+ "        \"gender\": \"male\",\r\n"
				+ "        \"status\": \"active\"\r\n"
				+ "}";
		Response response=restClient.post(WIRE_MOCK_BASE_URI, WIRE_MOCK_ENDPOINT,AuthType.NO_AUTH, ContentType.JSON,body, null, null);
		
	}
}
