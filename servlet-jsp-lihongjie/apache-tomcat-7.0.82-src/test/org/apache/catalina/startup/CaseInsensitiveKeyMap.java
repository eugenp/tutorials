/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.startup;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A Map implementation that uses case-insensitive (using {@link
 * Locale#ENGLISH}) strings as keys.
 * <p>
 * Keys must be instances of {@link String}. Note that this means that
 * <code>null</code> keys are not permitted.
 * <p>
 * This implementation is not thread-safe.
 *
 * @param <V> Type of values placed in this Map.
 */
public class CaseInsensitiveKeyMap<V> extends AbstractMap<String,V> {

    private final Map<Key,V> map = new HashMap<Key,V>();


    @Override
    public V get(Object key) {
        return map.get(Key.getInstance(key));
    }


    @Override
    public V put(String key, V value) {
        Key caseInsensitiveKey = Key.getInstance(key);
        if (caseInsensitiveKey == null) {
            throw new NullPointerException("Null key not permitted");
        }
        return map.put(caseInsensitiveKey, value);
    }


    /**
     * {@inheritDoc}
     * <p>
     * <b>Use this method with caution</b>. If the input Map contains duplicate
     * keys when the keys are compared in a case insensitive manner then some
     * values will be lost when inserting via this method.
     */
    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        super.putAll(m);
    }


    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(Key.getInstance(key));
    }


    @Override
    public V remove(Object key) {
        return map.remove(Key.getInstance(key));
    }


    @Override
    public Set<Entry<String, V>> entrySet() {
        return new EntrySet<V>(map.entrySet());
    }


    private static class EntrySet<V> extends AbstractSet<Entry<String,V>> {

        private final Set<Entry<Key,V>> entrySet;

        public EntrySet(Set<Map.Entry<Key,V>> entrySet) {
            this.entrySet = entrySet;
        }

        @Override
        public Iterator<Entry<String,V>> iterator() {
            return new EntryIterator<V>(entrySet.iterator());
        }

        @Override
        public int size() {
            return entrySet.size();
        }
    }


    private static class EntryIterator<V> implements Iterator<Entry<String,V>> {

        private final Iterator<Entry<Key,V>> iterator;

        public EntryIterator(Iterator<Entry<Key,V>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Entry<String,V> next() {
            Entry<Key,V> entry = iterator.next();
            return new EntryImpl<V>(entry.getKey().getKey(), entry.getValue());
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }


    private static class EntryImpl<V> implements Entry<String,V> {

        private final String key;
        private final V value;

        public EntryImpl(String key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }

    private static class Key {

        private final String key;
        private final String lcKey;

        private Key(String key) {
            this.key = key;
            this.lcKey = key.toLowerCase(Locale.ENGLISH);
        }

        public String getKey() {
            return key;
        }

        @Override
        public int hashCode() {
            return lcKey.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Key other = (Key) obj;
            return lcKey.equals(other.lcKey);
        }

        public static Key getInstance(Object o) {
            if (o instanceof String) {
                return new Key((String) o);
            }
            return null;
        }
    }
}
