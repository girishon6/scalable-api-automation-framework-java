package com.qa.api.utils;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class JsonSchemaValidator {

	public static boolean validateSchema(Response response,String filename) {
	
		try {
			response.then().assertThat().body(matchesJsonSchemaInClasspath(filename));
			System.out.println("Schema validation passed for "+filename);
			return true;
		}
		catch(Exception e) {
			System.out.println("Schema validation failed"+e.getMessage());
			return false;
		}
		
	
	}
}
