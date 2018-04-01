package cn.nonocast.cache;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;

public class AppCacheManager extends RedisCacheManager {
	private RedisOperations ops;

	public AppCacheManager(RedisOperations redisOperations) {
		super(redisOperations);
		this.ops = redisOperations;
		this.setUsePrefix(true);
	}

	public AppCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
		super(redisOperations, cacheNames);
		this.ops = redisOperations;
		this.setUsePrefix(true);
	}

	@Override
	protected RedisCache createCache(String cacheName) {
//		if(cacheName.equals("token")) {
//			return new RedisCache(cacheName, "token:".getBytes(), this.ops, 24*60*60);
//		}

		return super.createCache(cacheName);
	}
}
