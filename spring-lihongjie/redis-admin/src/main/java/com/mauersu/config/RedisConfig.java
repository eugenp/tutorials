package com.mauersu.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.Configuration;

import com.mauersu.exception.RedisInitException;

@Configuration
public class RedisConfig {

	private static Log log = LogFactory.getLog(RedisConfig.class);
	
	private final static String DEFAULT_REDIS_PROPERTIES_FILEPATH = RedisConfig.class.getClassLoader().getResource("/").getPath() + "/redis.properties";
	
	/*@Bean(name="redisProperties")*/
	//not-in-use now
	public Properties propertiesConfig() {
		Properties prop = new Properties();
		File file = new File(DEFAULT_REDIS_PROPERTIES_FILEPATH);
		InputStream is = null;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			is = new FileInputStream(file);
			prop.load(is);
		} catch (IOException e) {
			log.error("cannot load properties of file name: " + DEFAULT_REDIS_PROPERTIES_FILEPATH + " " +  e.toString());
			throw new RedisInitException(e);
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("close io have exception" +  e.toString());
					throw new RedisInitException(e);
				}
			}
		}
		return prop;
	}
	
	
	
}
