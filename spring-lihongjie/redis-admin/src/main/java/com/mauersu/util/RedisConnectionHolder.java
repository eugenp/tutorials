package com.mauersu.util;

import org.springframework.data.redis.connection.RedisConnection;

public class RedisConnectionHolder {
	private String serverName;
	private RedisConnection redisConnection;
	
	public RedisConnectionHolder(String serverName, RedisConnection redisConnection) {
		this.serverName = serverName;
		this.redisConnection = redisConnection;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public RedisConnection getRedisConnection() {
		return redisConnection;
	}
	public void setRedisConnection(RedisConnection redisConnection) {
		this.redisConnection = redisConnection;
	}
	
	
}
