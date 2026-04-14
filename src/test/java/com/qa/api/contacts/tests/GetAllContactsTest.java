package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsLogin;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class GetAllContactsTest extends BaseTest {

	private String token;
	
	@BeforeMethod
	public void generateToken() {
		
		ContactsLogin login = ContactsLogin.builder()
											.email("test200@gmail.com")
											.password("++herokucontactlist1--")
											.build();
		Response responsePost=restClient.post(CONTACTS_BASE_URI, AuthType.NO_AUTH, ContentType.JSON,CONTACTS_LOGIN_ENDPOINT,login, null, null);
		token=responsePost.jsonPath().getString("token");
		System.out.println("Token generated: "+token);
		
		ConfigManager.Setter("bearertoken", token);
		
	}
	
			
	@Test
	public void getAllContactsTest() {
		Response responseGet=restClient.get(CONTACTS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_ENDPOINT, null, null);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
	}
}
