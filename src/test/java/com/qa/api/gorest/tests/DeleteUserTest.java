package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.UserLombok;
import com.qa.api.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{

	
	@BeforeMethod
	public void generateToken() {
		String str="8800a23ac3bd24129b8bdde6cd423e0f6eeb3b8a445ed45c0e1868ef778b7fb0";
		ConfigManager.Setter("bearertoken", str);
	}
	
	@Test
	public void deleteUser() {
		
		// create the lombok user
		UserLombok user=UserLombok.builder()
									.name("Lewis")
									.email(StringUtil.getRandomEmail())
									.status("active")
									.gender("male")
									.build();
		// 1. Call post method to create the resource in the server
		System.out.println("-----post method to create the resource in the server---- ");
		Response responsePost=restClient.post(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT, user, null, null);
		String postId=responsePost.jsonPath().getString("id");
		Assert.assertNotNull(postId);
		Assert.assertEquals(responsePost.jsonPath().getString("name"), user.getName());
		
		System.out.println("--------------------");
		System.out.println("Generated resource id: "+postId);
		System.out.println("Generated resource name: "+responsePost.jsonPath().getString("name"));
		System.out.println("--------------------");
		
		//2. get call for verification
		System.out.println("-----get method to verify if the resource is created----- ");
		Response responseGet=restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT+"/"+postId, null, null);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), postId);
		
		//3.Delete user		
		System.out.println("-----Delete method to verify if the resource is deleted----- ");
		Response delResponse=restClient.delete(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT+"/"+postId, null, null);
		Assert.assertEquals(delResponse.statusCode(), 204);
		Assert.assertEquals(delResponse.getStatusLine(), "HTTP/1.1 204 No Content");
		
	}
}
