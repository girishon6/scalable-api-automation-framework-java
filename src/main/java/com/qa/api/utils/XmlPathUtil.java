package com.qa.api.utils;

import java.util.List;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XmlPathUtil {

	public static String getStringResponse(Response response) {
		String respString = response.getBody().asString();
		return respString;
	}

	public static String read(Response response, String xmlpath) {
		XmlPath xml = new XmlPath(getStringResponse(response));
		return xml.getString(xmlpath);

	}

	public static List<String> readList(Response response, String xmlpath) {
		XmlPath xml = new XmlPath(getStringResponse(response));
		return xml.getList(xmlpath);
	}

}
