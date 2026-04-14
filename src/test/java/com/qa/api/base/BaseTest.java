package com.qa.api.base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.qa.api.client.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;


public class BaseTest {
	
	public RestClient restClient;
	
	
	/*Base URI*/
	public static final String GOREST_BASE_URI="https://gorest.co.in";
	public static final String CONTACTS_BASE_URI="https://thinking-tester-contact-list.herokuapp.com";
	public static final String BASICAUTH_BASE_URI="https://the-internet.herokuapp.com";
	public static final String AMADEUS_BASE_URI="https://test.api.amadeus.com";
	
	/*Endpoint*/
	public static final String GOREST_ENDPOINT="/public/v2/users";
	public static final String CONTACTS_LOGIN_ENDPOINT="/users/login";
	public static final String CONTACTS_ENDPOINT="/contacts";
	public static final String BASICAUTH_ENDPOINT="/basic_auth";
	public static final String AMADEUS_TOKEN_ENDPOINT="/v1/security/oauth2/token";
	public static final String AMADEUS_END_POINT="/v1/reference-data/locations";
	public static final String GOREST_XML_ENDPOINT="/public/v2/users.xml";
	
	@BeforeClass
	public void setUp() {
		restClient=new RestClient();
	}
	
	@BeforeSuite
	public void setupAllureReport() {
		RestAssured.filters(new AllureRestAssured());
	}

}
