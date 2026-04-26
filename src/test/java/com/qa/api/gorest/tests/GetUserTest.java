package com.qa.api.gorest.tests;

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

public class GetUserTest extends BaseTest {

	/*@BeforeMethod
	public void generateToken() {
		String str="8800a23ac3bd24129b8bdde6cd423e0f6eeb3b8a445ed45c0e1868ef778b7fb0";
		ConfigManager.Setter("bearertoken", str);
	}*/
	
	@Test
	public void getUserTest() {
		Response response = restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT,
				null, null);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

	@Test
	public void getUserTestWithQueryParam() {

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", "Deeptanshu Bhat");
		queryParams.put("status", "active");

		Response response = restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT,
				queryParams, null);
		Assert.assertTrue(response.statusLine().contains("OK"));

	}

	@Test
	public void getSingleUserTest() {

		String id="8449231";

		Response response = restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT+"/"+id,
				null, null);
		Assert.assertTrue(response.statusLine().contains("OK"));

	}

}
