package com.bgi.rank.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtils {
	
	public static Properties getProperty(String fileName) {
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadAllProperties(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	public static String getValue(String fileName, String key) {
		Properties props = null;
		String result = null;
		try {
			props = PropertiesLoaderUtils.loadAllProperties(fileName);
			result = props.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		
	}
}
