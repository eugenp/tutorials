package com.baeldung.jackson.jsonurlreader.data;

public class Example {
    private String name;
    private Integer n;
    private Boolean real;

    public Example() {
    }

    public Example(String name, Integer n, Boolean real) {
        this.name = name;
        this.n = n;
        this.real = real;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Boolean getReal() {
        return real;
    }

    public void setReal(Boolean real) {
        this.real = real;
    }
}
