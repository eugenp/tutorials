package com.baeldung.generics;

public class MapEntry<K, V> {
    private K key;
    private V value;

    public MapEntry() {
        super();
    }

    // generic constructor with two parameters
    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // getters and setters
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}
