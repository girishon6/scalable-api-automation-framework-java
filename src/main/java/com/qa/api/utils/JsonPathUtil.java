package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.response.Response;

public class JsonPathUtil {
	
	public static String getStringResponse(Response response) {
		String responseStr=response.getBody().asString();
		return responseStr;
	}

	public static <T> T read(Response response,String jsonpath) {
		ReadContext ctx = JsonPath.parse(getStringResponse(response));
		return ctx.read(jsonpath);
	}
	
	public static <T> List<T> readList(Response response,String jsonpath) {
		ReadContext ctx = JsonPath.parse(getStringResponse(response));
		return ctx.read(jsonpath);
	}
	
	public static <T> List<Map<String,T>> readListOfMaps(Response response,String jsonpath) {
		ReadContext ctx = JsonPath.parse(getStringResponse(response));
		return ctx.read(jsonpath);
	}
}
