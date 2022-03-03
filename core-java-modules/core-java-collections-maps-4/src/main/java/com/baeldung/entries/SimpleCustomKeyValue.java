package com.baeldung.entries;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class SimpleCustomKeyValue<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;

    public SimpleCustomKeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        return this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleCustomKeyValue<?, ?> that = (SimpleCustomKeyValue<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SimpleCustomKeyValue.class.getSimpleName() + "[", "]").add("key=" + key).add("value=" + value).toString();
    }
}
