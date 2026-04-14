package com.qa.api.basicauth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BasicAuthTest extends BaseTest{

	@Test
	public void basicAuthTest() {
		Response responseGet=restClient.get(BASICAUTH_BASE_URI, AuthType.BASIC_AUTH, ContentType.ANY, BASICAUTH_ENDPOINT, null, null);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
	}
}
