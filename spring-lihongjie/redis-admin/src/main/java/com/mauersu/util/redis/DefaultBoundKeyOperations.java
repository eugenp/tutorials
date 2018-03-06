/*
 * Copyright 2011-2013 the original author or authors.
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundKeyOperations;
import org.springframework.data.redis.core.RedisOperations;

/**
 * Default {@link BoundKeyOperations} implementation. Meant for internal usage.
 * 
 * @author Costin Leau
 */
abstract class DefaultBoundKeyOperations<K> implements BoundKeyOperations<K> {

	private K key;
	private final RedisOperations<K, ?> ops;

	public DefaultBoundKeyOperations(K key, RedisOperations<K, ?> operations) {
		setKey(key);
		this.ops = operations;
	}

	public K getKey() {
		return key;
	}

	protected void setKey(K key) {
		this.key = key;
	}

	public Boolean expire(long timeout, TimeUnit unit) {
		return ops.expire(key, timeout, unit);
	}

	public Boolean expireAt(Date date) {
		return ops.expireAt(key, date);
	}

	public Long getExpire() {
		return ops.getExpire(key);
	}

	public Boolean persist() {
		return ops.persist(key);
	}

	public void rename(K newKey) {
		if (ops.hasKey(key)) {
			ops.rename(key, newKey);
		}
		key = newKey;
	}
}
