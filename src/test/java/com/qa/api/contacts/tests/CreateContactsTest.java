package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsLogin;
import com.qa.api.pojo.ContactsLombok;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateContactsTest extends BaseTest{

	private String token;
	
	@BeforeMethod
	public void generateToken() {
		
		ContactsLogin login= ContactsLogin.builder()
											.email("test200@gmail.com")
											.password("++herokucontactlist1--")
											.build();
		token=restClient.post(CONTACTS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_LOGIN_ENDPOINT, login, null, null)
						.then().extract().path("token");
		
		System.out.println("Token: "+token);
		ConfigManager.Setter("bearertoken", token);
	}
	
	@Test
	public void createContact() {
		ContactsLombok contacts= ContactsLombok.builder()
												.birthdate("1998-11-11")
												.city("mumbai")
												.street1("Test st1")
												.street2("Test st2")
												.firstName("Gerald")
												.lastName("Coetze")
												.phone("8945780232")
												.stateProvince("MH")
												.country("INDIA")
												.postalCode("9844")
												.email("test203@gmail.com")
												.build();
			Response responsePost=restClient.post(CONTACTS_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_ENDPOINT, contacts, null, null);
			Assert.assertEquals(responsePost.getStatusCode(), 201);
	}
}
