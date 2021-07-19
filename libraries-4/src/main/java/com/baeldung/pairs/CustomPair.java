package com.baeldung.pairs;

public class CustomPair {

    private String key;
    private String value;
    
    public CustomPair(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public Object[] getPair() {
        return new Object[] { this.key, this.value};
    }
}
