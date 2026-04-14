package com.qa.api.client;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {
	
	private static final ResponseSpecification responseSpec_201 = expect().statusCode(201);
	private static final ResponseSpecification responseSpec_200 = expect().statusCode(200);
	private static final ResponseSpecification responseSpec_200or404 = expect().statusCode(anyOf(equalTo(200),equalTo(404)));
	private static final ResponseSpecification responseSpec_204or404 = expect().statusCode(anyOf(equalTo(204),equalTo(404)));
	private static final ResponseSpecification responseSpec_200or201 = expect().statusCode(anyOf(equalTo(200),equalTo(201)));
	
	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {
		
			RequestSpecification request=RestAssured.given().log().all()
													.baseUri(baseUrl)
													.contentType(contentType);
			
			switch (authType) {
			case BEARER_TOKEN: 
				request.header("Authorization","Bearer "+ConfigManager.getter("bearertoken"));				
				break;
			case API_KEY: 
				request.header("x-api-key", "api key");				
				break;
			case NO_AUTH: 
				System.out.println("Auth is not required");				
				break;
			case BASIC_AUTH: 
				request.header("Authorization", "Basic "+generateBasicToken());				
				break;

			default:
					new APIException("====Auth type not supported====");
				break;
			}
			
			return request;
		
	}
	
	private String generateBasicToken() {
		String token=ConfigManager.getter("basicauthusername")+":"+ConfigManager.getter("basicauthpassword");
		//admin:admin--> YWRtaW46YWRtaW4=
		return Base64.getEncoder().encodeToString(token.getBytes());
	}
	
	private void applyParams(RequestSpecification request,Map<String,String> queryParams, Map<String,String> pathParams) {
		
		if(queryParams!=null) {
			request.queryParams(queryParams);
		}
		if(pathParams!=null) {
			request.pathParams(pathParams);
		}
		
	}
	
	/**
	 * This method is used to invoke api get method
	 * @param baseUrl
	 * @param authType
	 * @param contentType
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @return it returns a response type
	 */
	public Response get(String baseUrl, AuthType authType, 
					ContentType contentType, String endPoint,
					Map<String,String>queryParams,
					Map<String,String>pathParams) {
		
		RequestSpecification request=setupRequest(baseUrl, authType, contentType);
		applyParams(request,queryParams,pathParams);
		Response response=request.get(endPoint).then().spec(responseSpec_200).extract().response();
		response.prettyPrint();
		return response;
	}
	
	/**
	 * This method is used to create a resource in server side. It uses the String body type or pojo body type.
	 * @param <T>
	 * @param baseUrl
	 * @param authType
	 * @param contentType
	 * @param endPoint
	 * @param bodyContent
	 * @param queryParams
	 * @param pathParams
	 * @return It returns the response for post method
	 */
	@Step("Post method to create user with any content, url:{0},")
	public <T> Response post(String baseUrl, AuthType authType, 
			ContentType contentType, String endPoint, T bodyContent,
			Map<String,String>queryParams,
			Map<String,String>pathParams) {
		
		RequestSpecification reqSpec=setupRequest(baseUrl, authType, contentType);
		applyParams(reqSpec,queryParams,pathParams);
		reqSpec.body(bodyContent);
		Response response=reqSpec.post(endPoint).then().spec(responseSpec_200or201).extract().response();
		response.prettyPrint();
		
		return response;
	}
	
	/**
	 * This method is used to create a resource in server side. It uses the File body type.
	 * @param baseUrl
	 * @param authType
	 * @param contentType
	 * @param endPoint
	 * @param bodyContent
	 * @param queryParams
	 * @param pathParams
	 * @return It returns the response for post method
	 */
	
	public Response post(String baseUrl, AuthType authType, 
			ContentType contentType, String endPoint, File bodyContent,
			Map<String,String>queryParams,
			Map<String,String>pathParams) {
		
		RequestSpecification reqSpec=setupRequest(baseUrl, authType, contentType);
		applyParams(reqSpec,queryParams,pathParams);
		Response response=reqSpec.body(bodyContent)
		.post(endPoint).then().spec(responseSpec_200or201).extract().response();
		response.prettyPrint();
		
		return response;
	}
	
	public Response post(String baseUrl, AuthType authType, 
							 ContentType contentType, String endPoint,
							 String grant_type, String client_id, String client_secret) {
		
		Response response=RestAssured.given().contentType(contentType)
		.formParam("grant_type", grant_type)
		.formParam("client_id", client_id)
		.formParam("client_secret", client_secret)
		.when().post(baseUrl+endPoint);
		
		response.prettyPrint();
		
		return response;
	}
	
	/**
	 * This method is used to update the already existing resource in the server 
	 * @param baseUrl
	 * @param authType
	 * @param contentType
	 * @param endPoint
	 * @param bodyContent
	 * @param queryParams
	 * @param pathParams
	 * @return It returns the response type after updating the existing the resource
	 */
	public <T> Response put(String baseUrl, AuthType authType, 
			ContentType contentType, String endPoint, T bodyContent,
			Map<String,String>queryParams,
			Map<String,String>pathParams) {
		
		RequestSpecification reqSpec=setupRequest(baseUrl, authType, contentType);
		applyParams(reqSpec,queryParams,pathParams);
		reqSpec.body(bodyContent);
		Response response=reqSpec.put(endPoint).then().spec(responseSpec_200).extract().response();
		response.prettyPrint();
		
		return response;
	}
	
	/**
	 * This method is used to delete the existing resource in the server
	 * @param baseUrl
	 * @param authType
	 * @param contentType
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @return It returns the status code 204 upon successful deletion or returns 404 when the resource is not found
	 */
	public Response delete(String baseUrl, AuthType authType, 
			ContentType contentType, String endPoint,
			Map<String,String>queryParams,
			Map<String,String>pathParams) {
		
		RequestSpecification reqSpec=setupRequest(baseUrl, authType, contentType);
		applyParams(reqSpec,queryParams,pathParams);
		Response response=reqSpec.delete(endPoint).then().spec(responseSpec_204or404).extract().response();
		response.prettyPrint();
		
		return response;
	}
	
	

}
