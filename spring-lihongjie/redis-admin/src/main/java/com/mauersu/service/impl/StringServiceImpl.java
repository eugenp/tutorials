package com.mauersu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mauersu.dao.RedisDao;
import com.mauersu.service.HashService;
import com.mauersu.service.StringService;

@Service
public class StringServiceImpl implements StringService {

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public void delValue(String serverName, int dbIndex, String key) {
		redisDao.delRedisValue(serverName, dbIndex, key);
	}

	@Override
	public void updateValue(String serverName, int dbIndex, String key, String value) {
		redisDao.updateValue(serverName, dbIndex, key, value);
	}
	
}
