package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.UserLombok;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


@Epic("Epic 100: Go rest user API feature")
@Story("US 100: CreateUserTest")
public class CreateUserTest extends BaseTest {
	
	String email=StringUtil.getRandomEmail();
	UserLombok user=new UserLombok("Trihara",email,"male","active");
	UserLombok userTwo = UserLombok.builder()
									.name("testname")
									.email(email)
									.gender("male")
									.status("active")
									.build();
	
	
	
	@BeforeMethod
	public void generateToken() {
		String str="8800a23ac3bd24129b8bdde6cd423e0f6eeb3b8a445ed45c0e1868ef778b7fb0";
		ConfigManager.Setter("bearertoken", str);
	}
	
	@DataProvider
	public Object[][] inputData() {
		return new Object[][] {
			{"Mihara","male","active"},
			{"Fihara","female","inactive"}
		};
	}
	
	@DataProvider
	public Object[][] inputDataExcel() {
		return ExcelUtil.readExcelData(AppConstants.sheetname);
	}
	
	@Description("Create User test using POJO")
	@Owner("Girish")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="inputDataExcel")
	public void createUserTestUsingPojo(String name, String gender, String status) {
		UserLombok usernew=new UserLombok(name,StringUtil.getRandomEmail(),gender,status);
		
		Response resp=restClient.post(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT, usernew, null, null);
		//Assert.assertTrue(resp.getStatusLine().contains("Created"));
		JsonPath js=resp.jsonPath();//use jsonpath to parse the response
		Assert.assertEquals(js.getString("name"),name);// assert using getter method
		
	}
	
	@Description("Create User test using File as body")
	@Owner("Girish")
	@Severity(SeverityLevel.NORMAL)
	@Test(enabled = false)
	public void createUserTestUsingFile() throws IOException {
		
		/*
		 * String randomEmail=StringUtil.getRandomEmail(); String str=new
		 * String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")))
		 * ; String updatedEmail=str.replace("{email}", randomEmail);
		 */
		 
		
		File file = new File("./src/test/resources/jsons/user.json");
		Response resp=restClient.post(GOREST_BASE_URI, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_ENDPOINT, file, null, null);
		JsonPath js=resp.jsonPath();
		Assert.assertEquals(js.getString("name"),"John");
		
		
	}

}
