package com.qa.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;


public class ObjectMapperUtil {
	
	private static ObjectMapper object=new ObjectMapper();
	
	public static <T> T deserialize(Response response, Class<T> classname) {
		
		try {
			return object.readValue(response.getBody().asString(), classname);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Deserialization failed...."+classname,e);
		}
		
	} 

}
