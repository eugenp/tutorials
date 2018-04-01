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
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

/**
 * Default implementation for {@link BoundSetOperations}.
 * 
 * @author Costin Leau
 * @author Christoph Strobl
 */
class DefaultBoundSetOperations<K, V> extends DefaultBoundKeyOperations<K> implements BoundSetOperations<K, V> {

	private final SetOperations<K, V> ops;

	/**
	 * Constructs a new <code>DefaultBoundSetOperations</code> instance.
	 * 
	 * @param key
	 * @param operations
	 */
	DefaultBoundSetOperations(K key, RedisOperations<K, V> operations) {
		super(key, operations);
		this.ops = operations.opsForSet();
	}

	public Long add(V... values) {
		return ops.add(getKey(), values);
	}

	public Set<V> diff(K key) {
		return ops.difference(getKey(), key);
	}

	public Set<V> diff(Collection<K> keys) {
		return ops.difference(getKey(), keys);
	}

	public void diffAndStore(K key, K destKey) {
		ops.differenceAndStore(getKey(), key, destKey);
	}

	public void diffAndStore(Collection<K> keys, K destKey) {
		ops.differenceAndStore(getKey(), keys, destKey);
	}

	public RedisOperations<K, V> getOperations() {
		return ops.getOperations();
	}

	public Set<V> intersect(K key) {
		return ops.intersect(getKey(), key);
	}

	public Set<V> intersect(Collection<K> keys) {
		return ops.intersect(getKey(), keys);
	}

	public void intersectAndStore(K key, K destKey) {
		ops.intersectAndStore(getKey(), key, destKey);
	}

	public void intersectAndStore(Collection<K> keys, K destKey) {
		ops.intersectAndStore(getKey(), keys, destKey);
	}

	public Boolean isMember(Object o) {
		return ops.isMember(getKey(), o);
	}

	public Set<V> members() {
		return ops.members(getKey());
	}

	public Boolean move(K destKey, V value) {
		return ops.move(getKey(), value, destKey);
	}

	public V randomMember() {
		return ops.randomMember(getKey());
	}

	public Set<V> distinctRandomMembers(long count) {
		return ops.distinctRandomMembers(getKey(), count);
	}

	public List<V> randomMembers(long count) {
		return ops.randomMembers(getKey(), count);
	}

	public Long remove(Object... values) {
		return ops.remove(getKey(), values);
	}

	public V pop() {
		return ops.pop(getKey());
	}

	public Long size() {
		return ops.size(getKey());
	}

	public Set<V> union(K key) {
		return ops.union(getKey(), key);
	}

	public Set<V> union(Collection<K> keys) {
		return ops.union(getKey(), keys);
	}

	public void unionAndStore(K key, K destKey) {
		ops.unionAndStore(getKey(), key, destKey);
	}

	public void unionAndStore(Collection<K> keys, K destKey) {
		ops.unionAndStore(getKey(), keys, destKey);
	}

	public DataType getType() {
		return DataType.SET;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.core.BoundSetOperations#sScan(org.springframework.data.redis.core.ScanOptions)
	 */
	@Override
	public Cursor<V> scan(ScanOptions options) {
		return ops.scan(getKey(), options);
	}
}
