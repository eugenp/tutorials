/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.res.StringManager;

/**
 * Implementation of <strong>java.util.Map</strong> that includes a
 * <code>locked</code> property.  This class can be used to safely expose
 * Catalina internal parameter map objects to user classes without having
 * to clone them in order to avoid modifications.  When first created, a
 * <code>ParmaeterMap</code> instance is not locked.
 *
 * @author Craig R. McClanahan
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public final class ParameterMap<K,V> implements Map<K,V>, Serializable {

    private static final long serialVersionUID = 2L;

    private final Map<K,V> delegatedMap;

    private final Map<K,V> unmodifiableDelegatedMap;


    /**
     * Construct a new, empty map with the default initial capacity and
     * load factor.
     */
    public ParameterMap() {
        delegatedMap = new LinkedHashMap<K, V>();
        unmodifiableDelegatedMap = Collections.unmodifiableMap(delegatedMap);
    }


    /**
     * Construct a new, empty map with the specified initial capacity and
     * default load factor.
     *
     * @param initialCapacity The initial capacity of this map
     */
    public ParameterMap(int initialCapacity) {
        delegatedMap = new LinkedHashMap<K, V>(initialCapacity);
        unmodifiableDelegatedMap = Collections.unmodifiableMap(delegatedMap);
    }


    /**
     * Construct a new, empty map with the specified initial capacity and
     * load factor.
     *
     * @param initialCapacity The initial capacity of this map
     * @param loadFactor The load factor of this map
     */
    public ParameterMap(int initialCapacity, float loadFactor) {
        delegatedMap = new LinkedHashMap<K, V>(initialCapacity, loadFactor);
        unmodifiableDelegatedMap = Collections.unmodifiableMap(delegatedMap);
    }


    /**
     * Construct a new map with the same mappings as the given map.
     *
     * @param map Map whose contents are duplicated in the new map
     */
    public ParameterMap(Map<K,V> map) {
        delegatedMap = new LinkedHashMap<K, V>(map);
        unmodifiableDelegatedMap = Collections.unmodifiableMap(delegatedMap);
    }


    /**
     * The current lock state of this parameter map.
     */
    private boolean locked = false;


    /**
     * Get the locked state of this parameter map.
     *
     * @return {@code true} if the Map is locked, otherwise {@code false}
     */
    public boolean isLocked() {
        return locked;
    }


    /**
     * Set the locked state of this parameter map.
     *
     * @param locked The new locked state
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    /**
     * The string manager for this package.
     */
    private static final StringManager sm = StringManager.getManager("org.apache.catalina.util");


    /**
     * {@inheritDoc}
     *
     * @exception IllegalStateException if this map is currently locked
     */
    @Override
    public void clear() {
        checkLocked();
        delegatedMap.clear();
    }


    /**
     * {@inheritDoc}
     *
     * @exception IllegalStateException if this map is currently locked
     */
    @Override
    public V put(K key, V value) {
        checkLocked();
        return delegatedMap.put(key, value);
    }


    /**
     * {@inheritDoc}
     *
     * @exception IllegalStateException if this map is currently locked
     */
    @Override
    public void putAll(Map<? extends K,? extends V> map) {
        checkLocked();
        delegatedMap.putAll(map);
    }


    /**
     * {@inheritDoc}
     *
     * @exception IllegalStateException if this map is currently locked
     */
    @Override
    public V remove(Object key) {
        checkLocked();
        return delegatedMap.remove(key);
    }


    private void checkLocked() {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
    }


    @Override
    public int size() {
        return delegatedMap.size();
    }


    @Override
    public boolean isEmpty() {
        return delegatedMap.isEmpty();
    }


    @Override
    public boolean containsKey(Object key) {
        return delegatedMap.containsKey(key);
    }


    @Override
    public boolean containsValue(Object value) {
        return delegatedMap.containsValue(value);
    }


    @Override
    public V get(Object key) {
        return delegatedMap.get(key);
    }


    /**
     * {@inheritDoc}
     * <p>
     * Returns an <strong>unmodifiable</strong> {@link Set} view of the keys
     * contained in this map if it is locked.
     */
    @Override
    public Set<K> keySet() {
        if (locked) {
            return unmodifiableDelegatedMap.keySet();
        }

        return delegatedMap.keySet();
    }


    /**
     * {@inheritDoc}
     * <p>
     * Returns an <strong>unmodifiable</strong> {@link Collection} view of the
     * values contained in this map if it is locked.
     */
    @Override
    public Collection<V> values() {
        if (locked) {
            return unmodifiableDelegatedMap.values();
        }

        return delegatedMap.values();
    }


    /**
     * {@inheritDoc}
     * <p>
     * Returns an <strong>unmodifiable</strong> {@link Set} view of the mappings
     * contained in this map if it is locked.
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        if (locked) {
            return unmodifiableDelegatedMap.entrySet();
        }

        return delegatedMap.entrySet();
    }
}
