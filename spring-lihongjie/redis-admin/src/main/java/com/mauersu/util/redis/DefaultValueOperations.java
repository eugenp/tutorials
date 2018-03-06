/*
 * Copyright 2011-2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mauersu.util.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Default implementation of {@link ValueOperations}.
 * 
 * @author Costin Leau
 * @author Jennifer Hickey
 * @author Christoph Strobl
 */
class DefaultValueOperations<K, V> extends AbstractOperations<K, V> implements ValueOperations<K, V> {

	DefaultValueOperations(RedisTemplate<K, V> template) {
		super(template);
	}
	private volatile int dbIndex;
	DefaultValueOperations(RedisTemplate<K, V> template, int dbIndex) {
		super(template);
		this.dbIndex = dbIndex;
	}

	public V get(final Object key) {

		return execute(new ValueDeserializingRedisCallback(key) {

			protected byte[] inRedis(byte[] rawKey, RedisConnection connection) {
				connection.select(dbIndex);
				return connection.get(rawKey);
			}
		}, true);
	}

	public V getAndSet(K key, V newValue) {
		final byte[] rawValue = rawValue(newValue);
		return execute(new ValueDeserializingRedisCallback(key) {

			protected byte[] inRedis(byte[] rawKey, RedisConnection connection) {
				connection.select(dbIndex);
				return connection.getSet(rawKey, rawValue);
			}
		}, true);
	}

	public Long increment(K key, final long delta) {
		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.incrBy(rawKey, delta);
			}
		}, true);
	}

	public Double increment(K key, final double delta) {
		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Double>() {
			public Double doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.incrBy(rawKey, delta);
			}
		}, true);
	}

	public Integer append(K key, String value) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawString = rawString(value);

		return execute(new RedisCallback<Integer>() {

			public Integer doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				final Long result = connection.append(rawKey, rawString); 				
				return ( result != null ) ? result.intValue() : null; 
			}
		}, true);
	}

	public String get(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		byte[] rawReturn = execute(new RedisCallback<byte[]>() {

			public byte[] doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.getRange(rawKey, start, end);
			}
		}, true);

		return deserializeString(rawReturn);
	}

	public List<V> multiGet(Collection<K> keys) {
		if (keys.isEmpty()) {
			return Collections.emptyList();
		}

		final byte[][] rawKeys = new byte[keys.size()][];

		int counter = 0;
		for (K hashKey : keys) {
			rawKeys[counter++] = rawKey(hashKey);
		}

		List<byte[]> rawValues = execute(new RedisCallback<List<byte[]>>() {

			public List<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.mGet(rawKeys);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public void multiSet(Map<? extends K, ? extends V> m) {
		if (m.isEmpty()) {
			return;
		}

		final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());

		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			rawKeys.put(rawKey(entry.getKey()), rawValue(entry.getValue()));
		}

		execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				connection.mSet(rawKeys);
				return null;
			}
		}, true);
	}

	public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m) {
		if (m.isEmpty()) {
			return true;
		}

		final Map<byte[], byte[]> rawKeys = new LinkedHashMap<byte[], byte[]>(m.size());

		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			rawKeys.put(rawKey(entry.getKey()), rawValue(entry.getValue()));
		}

		return execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.mSetNX(rawKeys);
			}
		}, true);
	}

	public void set(K key, V value) {
		final byte[] rawValue = rawValue(value);
		execute(new ValueDeserializingRedisCallback(key) {

			protected byte[] inRedis(byte[] rawKey, RedisConnection connection) {
				connection.select(dbIndex);
				connection.set(rawKey, rawValue);
				return null;
			}
		}, true);
	}

	public void set(K key, V value, final long timeout, final TimeUnit unit) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(dbIndex);
				potentiallyUsePsetEx(connection);
				return null;
			}

			public void potentiallyUsePsetEx(RedisConnection connection) {

				if (!TimeUnit.MILLISECONDS.equals(unit) || !failsafeInvokePsetEx(connection)) {
					connection.select(dbIndex);
					connection.setEx(rawKey, TimeoutUtils.toSeconds(timeout, unit), rawValue);
				}
			}

			private boolean failsafeInvokePsetEx(RedisConnection connection) {

				boolean failed = false;
				try {
					connection.select(dbIndex);
					connection.pSetEx(rawKey, timeout, rawValue);
				} catch (UnsupportedOperationException e) {
					// in case the connection does not support pSetEx return false to allow fallback to other operation.
					failed = true;
				}
				return !failed;
			}

		}, true);
	}

	public Boolean setIfAbsent(K key, V value) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		return execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(dbIndex);
				return connection.setNX(rawKey, rawValue);
			}
		}, true);
	}

	public void set(K key, final V value, final long offset) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				connection.setRange(rawKey, rawValue, offset);
				return null;
			}
		}, true);
	}

	public Long size(K key) {
		final byte[] rawKey = rawKey(key);

		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.strLen(rawKey);
			}
		}, true);
	}
}
