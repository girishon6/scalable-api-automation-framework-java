package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.UserLombok;
import com.qa.api.utils.JsonSchemaValidator;
import com.qa.api.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestApiSchemaTest extends BaseTest{

	@BeforeMethod
	public void setToken() {
		ConfigManager.Setter("bearertoken", "8800a23ac3bd24129b8bdde6cd423e0f6eeb3b8a445ed45c0e1868ef778b7fb0");
	}
	
	@Test
	public void goRestGetCallAPISchemaTest() {
		
		Response response=restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT, null, null);
		boolean schemaResult=JsonSchemaValidator.validateSchema(response,"jsons/gorestGetCall.json");
		Assert.assertTrue(schemaResult);
	}
	
	@Test
	public void goRestPostCallAPISchemaTest() {
		
		String email=StringUtil.getRandomEmail();
		UserLombok user=new UserLombok("Sahara",email,"male","active");
		
		Response response=restClient.post(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT, user, null, null);
		boolean schemaResult=JsonSchemaValidator.validateSchema(response,"jsons/gorestPostCall.json");
		Assert.assertTrue(schemaResult);
	}
}
