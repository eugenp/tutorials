package com.mauersu.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mauersu.exception.RedisConnectionException;
import com.mauersu.util.RedisApplication;

@Service
public class RedisTemplateFactory extends RedisApplication {
	
	private static Log log = LogFactory.getLog(RedisTemplateFactory.class);
	
	protected static RedisTemplate<String, Object> getRedisTemplate(String redisName) {
		RedisTemplate<String, Object> redisTemplate = redisTemplatesMap.get(redisName);
		if(redisTemplate==null) {
			log.error("redisTemplate==null" + ". had not connected to " + redisName + " this redis server now.");
			throw new RedisConnectionException("had not connected to " + redisName + " this redis server now.");
		}
		return redisTemplate;
	}
	
	private static void validate(int dbIndex) {
		if(0> dbIndex || dbIndex> 15) {
			log.error("0> dbIndex || dbIndex> 15" + "redis dbIndex is invalid : " + dbIndex);
			throw new RedisConnectionException("redis dbIndex is invalid : " + dbIndex);
		}
		return ;
	}
	
}
