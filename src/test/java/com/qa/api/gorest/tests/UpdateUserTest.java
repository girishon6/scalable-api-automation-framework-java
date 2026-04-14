package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.UserLombok;
import com.qa.api.utils.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {
	
	

	@Test
	public void updateUserTest() {
		
		// create the lombok user
		UserLombok user = UserLombok.builder()
									.name("Max")
									.gender("male")
									.email(StringUtil.getRandomEmail())
									.status("active")
									.build();
		
		// 1. Call post method to create the resource in the server
		System.out.println("-----post method to create the resource in the server---- ");
		
		Response responsePost = restClient.post(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT, user, null, null);
		JsonPath js=responsePost.jsonPath();
		String postId=js.getString("id");
		Assert.assertNotNull(postId);
		Assert.assertEquals(js.getString("name"), user.getName());
				
		System.out.println("--------------------");
		System.out.println("Generated resource id: "+postId);
		System.out.println("Generated resource name: "+js.getString("name"));
		System.out.println("--------------------");
		
		System.out.println("-----get method to verify if the resource is created----- ");
		//2. Verify if the resource is created by calling get method
		Response responseGet=restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT+"/"+postId, null, null);
		Assert.assertEquals(responseGet.jsonPath().getString("name"),"Max");
		Assert.assertEquals(responseGet.jsonPath().getString("id"), postId);
				
		
		//3. update the user		
		user.setName("Lando");
		user.setStatus("inactive");
		
		System.out.println("-----put method to update the resource----- ");
		//4.update the user using put method
		Response responsePut=restClient.put(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT+"/"+postId, user, null, null);
		JsonPath jsPut=responsePut.jsonPath();
		String name=jsPut.getString("name");
		String status=jsPut.getString("status");
		Assert.assertEquals(name, user.getName());
		Assert.assertEquals(status, user.getStatus());
		
		
	}

}
