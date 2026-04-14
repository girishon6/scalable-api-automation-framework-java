package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties properties=new Properties();
	
	static {
		InputStream ip=ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties");
		if(ip!=null) {
			try {
				properties.load(ip);
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
