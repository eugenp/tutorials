package com.mauersu.util.redis;

import java.util.Collection;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.mauersu.exception.MethodNotSupportException;
import com.mauersu.util.RedisApplication;

public class MyRedisTemplate<K, V> extends RedisTemplate<K, V> {

	private volatile int dbIndex;
	
	@Override
	public ValueOperations<K, V> opsForValue() {
		int dbIndex = RedisApplication.redisConnectionDbIndex.get();
		return new DefaultValueOperations<K, V>(this, dbIndex);
	}
	@Override
	public ListOperations<K, V> opsForList() {
		int dbIndex = RedisApplication.redisConnectionDbIndex.get();
		return new DefaultListOperations<K, V>(this, dbIndex);
	}
	@Override
	public SetOperations<K, V> opsForSet() {
		int dbIndex = RedisApplication.redisConnectionDbIndex.get();
		return new DefaultSetOperations<K, V>(this, dbIndex);
	}
	@Override
	public ZSetOperations<K, V> opsForZSet() {
		int dbIndex = RedisApplication.redisConnectionDbIndex.get();
		return new DefaultZSetOperations<K, V>(this, dbIndex);
	}
	@Override
	public <HK, HV> HashOperations<K, HK, HV> opsForHash() {
		int dbIndex = RedisApplication.redisConnectionDbIndex.get();
		return new DefaultHashOperations<K, HK, HV>(this, dbIndex);
	}
	
	//--bound
	@Override
	public BoundListOperations<K, V> boundListOps(K key) {
		throw new MethodNotSupportException("myRedisTemplate not support this method : boundListOps(K key) , please use opsForXX");
		//return new DefaultBoundListOperations<K, V>(key, this);
	}
	@Override
	public BoundSetOperations<K, V> boundSetOps(K key) {
		throw new MethodNotSupportException("myRedisTemplate not support this method : boundSetOps(K key) , please use opsForXX");
		//return new DefaultBoundSetOperations<K, V>(key, this);
	}

	@Override
	public BoundZSetOperations<K, V> boundZSetOps(K key) {
		throw new MethodNotSupportException("myRedisTemplate not support this method : boundZSetOps(K key) , please use opsForXX");
		//return new DefaultBoundZSetOperations<K, V>(key, this);
	}
	@Override
	public <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key) {
		throw new MethodNotSupportException("myRedisTemplate not support this method : boundHashOps(K key) , please use opsForXX");
		//return new DefaultBoundHashOperations<K, HK, HV>(key, this);
	}
	@Override
	public BoundValueOperations<K, V> boundValueOps(K key) {
		throw new MethodNotSupportException("myRedisTemplate not support this method : boundValueOps(K key) , please use opsForXX");
		//return new DefaultBoundValueOperations<K, V>(key, this);
	}
	
	// delete 
	@Override
	public void delete(K key) {
		final byte[] rawKey = rawKey(key);

		execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection) {
				int dbIndex = RedisApplication.redisConnectionDbIndex.get();
				connection.select(dbIndex);
				connection.del(rawKey);
				return null;
			}
		}, true);
	}
	
	@Override
	public void delete(Collection<K> keys) {
		if (CollectionUtils.isEmpty(keys)) {
			return;
		}

		final byte[][] rawKeys = rawKeys(keys);

		execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection) {
				int dbIndex = RedisApplication.redisConnectionDbIndex.get();
				connection.select(dbIndex);
				connection.del(rawKeys);
				return null;
			}
		}, true);
	}
	
	private RedisSerializer keySerializer = new StringRedisSerializer();
	
	@SuppressWarnings("unchecked")
	private byte[] rawKey(Object key) {
		Assert.notNull(key, "non null key required");
		if (keySerializer == null && key instanceof byte[]) {
			return (byte[]) key;
		}
		return keySerializer.serialize(key);
	}

	private byte[][] rawKeys(Collection<K> keys) {
		final byte[][] rawKeys = new byte[keys.size()][];

		int i = 0;
		for (K key : keys) {
			rawKeys[i++] = rawKey(key);
		}

		return rawKeys;
	}
}
