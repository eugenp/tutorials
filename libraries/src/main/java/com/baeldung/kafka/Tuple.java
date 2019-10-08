package com.baeldung.kafka;

public class Tuple {

    private String key;
    private Integer value;

    private Tuple(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public static Tuple of(String key, Integer value){
        return new Tuple(key,value);
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
