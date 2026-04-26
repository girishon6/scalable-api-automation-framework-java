package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties properties=new Properties();
	
	static {
		
		//mvn clean install -Denv=qa/stage/uat
		String envName=System.getProperty("env", "prod");
		System.out.println("Running the testcacses on :"+envName);
		String fileName="config_"+envName+".properties";
		
		InputStream ip=ConfigManager.class.getClassLoader().getResourceAsStream(fileName);
		if(ip!=null) {
			try {
				properties.load(ip);
				System.out.println("properties====>"+properties);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static String getter(String key) {
		String propVal=properties.getProperty(key);
		return propVal;
	}
	
	public static void Setter(String key, String value) {
		properties.setProperty(key, value);
	}

}
