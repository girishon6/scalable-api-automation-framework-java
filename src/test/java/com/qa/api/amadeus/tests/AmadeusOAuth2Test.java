package com.qa.api.amadeus.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusOAuth2Test extends BaseTest {
	
	@BeforeMethod
	public void getToken() {
		Response response=restClient.post(AMADEUS_BASE_URI, AuthType.NO_AUTH, ContentType.URLENC, AMADEUS_TOKEN_ENDPOINT, ConfigManager.getter("granttype"), ConfigManager.getter("clientid"), ConfigManager.getter("clientsecret"));
		
		String accessToken=response.jsonPath().getString("access_token");
		System.out.println("Access token:"+accessToken);
		
		ConfigManager.Setter("bearertoken", accessToken);
	
	}
	
	@Test
	public void amadeusAirportAndCitySearch() {
		
		Map<String,String>queryParams=new HashMap<String,String>();
		queryParams.put("subType","CITY");
		queryParams.put("keyword","MUC");
		
		Response responseGet=restClient.get(AMADEUS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.ANY, AMADEUS_END_POINT, queryParams, null);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
	}

}
