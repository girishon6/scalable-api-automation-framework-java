package com.qa.api.contacts.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsLogin;
import com.qa.api.utils.JsonPathUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITestWithJsonPath extends BaseTest{

	private String token;
	
	@BeforeMethod
	public void getToken() {
		
		ContactsLogin contacts= ContactsLogin.builder()
											  .email("test200@gmail.com")
											  .password("++herokucontactlist1--")
											  .build();
		Response responsePost=restClient.post(CONTACTS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_LOGIN_ENDPOINT, contacts, null, null);
		token=responsePost.jsonPath().getString("token");
		
		ConfigManager.Setter("bearertoken", token);
	}
	

	@Test
	public void contactsAPITestWithJsonpath() {
		
		Response responseGet=restClient.get(CONTACTS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_ENDPOINT, null, null);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		
		List<String>firstname=JsonPathUtil.readList(responseGet, "$.[*].firstName");
				
		for(String fname:firstname) {
			System.out.println(fname);
		}
		
		List<Map<String,String>> fistAndLastName=JsonPathUtil.readListOfMaps(responseGet, "$.[*].['firstName','lastName']");
		for(Map<String,String> names:fistAndLastName) {
			System.out.println("FirstName"+":"+names.get("firstName"));
			System.out.println("LastName"+":"+names.get("lastName"));
			System.out.println("--------------------------");
		}
		
		Object fname = JsonPathUtil.read(responseGet, "$.[?(@.birthdate<'1986-12-12')].firstName");
		System.out.println(fname);
		
	}
}
