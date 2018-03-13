package com.mauersu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

	@Autowired
	private Environment env;
	
	public String getProperty(String key) {
		String value = env.getProperty(key);
		return value;
	}
	
	
}
