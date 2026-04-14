package com.qa.api.gorest.tests;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.utils.XmlPathUtil;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class GetUserWithXMLPathTest extends BaseTest{
	
	
	
	@BeforeMethod
	public void generateToken() {
		String str="8800a23ac3bd24129b8bdde6cd423e0f6eeb3b8a445ed45c0e1868ef778b7fb0";
		ConfigManager.Setter("bearertoken", str);
	}
	
	@Test
	public void getUserWithXmlPathTest() {
		
		Response response=restClient.get(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_XML_ENDPOINT, null, null);
		
		String name = XmlPathUtil.read(response, "objects.object[0].name");	
		System.out.println(name);
		
		List<String> readIdList = XmlPathUtil.readList(response, "objects.object.id");
		for(String id:readIdList) {
			System.out.println(id);
		}
		
	}

}
