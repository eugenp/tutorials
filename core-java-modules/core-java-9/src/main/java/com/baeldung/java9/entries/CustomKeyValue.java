package com.baeldung.java9.entries;

public interface CustomKeyValue<K, V> {

    K getKey();

    V getValue();

    void setKey(K key);

    void setValue(V value);
}
