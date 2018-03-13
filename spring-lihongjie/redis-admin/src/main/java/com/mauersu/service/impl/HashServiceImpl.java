package com.mauersu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mauersu.dao.RedisDao;
import com.mauersu.service.HashService;

@Service
public class HashServiceImpl implements HashService {

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public void delHashField(String serverName, int dbIndex, String key, String field) {
		redisDao.delRedisHashField(serverName, dbIndex, key, field);
	}

	@Override
	public void updateHashField(String serverName, int dbIndex, String key, String field, String value) {
		redisDao.updateHashField(serverName, dbIndex, key, field, value);
	}
	
}
