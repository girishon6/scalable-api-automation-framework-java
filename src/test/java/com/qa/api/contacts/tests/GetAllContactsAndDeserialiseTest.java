package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsLogin;
import com.qa.api.pojo.ContactsLombok;
import com.qa.api.utils.ObjectMapperUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAllContactsAndDeserialiseTest extends BaseTest{

	
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
	public void getAllContactsAndDeserialiseTest() {
		

		Response responsePost=restClient.get(CONTACTS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_ENDPOINT, null, null);
		Assert.assertEquals(responsePost.getStatusCode(), 200);
		
		ContactsLombok[] contactsLombok=ObjectMapperUtil.deserialize(responsePost, ContactsLombok[].class);
		
		System.out.println("----Deserialzed data below-----");
		for(ContactsLombok contact:contactsLombok) {
			
			System.out.println(contact.getId());
			System.out.println(contact.getBirthdate());
			System.out.println(contact.getCity());
			System.out.println(contact.getCountry());
			System.out.println(contact.getEmail());
			System.out.println(contact.getFirstName());
			System.out.println(contact.getLastName());
			System.out.println(contact.getPhone());
			System.out.println(contact.getPostalCode());
			System.out.println(contact.getStateProvince());
			System.out.println(contact.getStreet1());
			System.out.println(contact.getStreet2());
			System.out.println("-------------------------");
			
			
			
		}
	}

}
