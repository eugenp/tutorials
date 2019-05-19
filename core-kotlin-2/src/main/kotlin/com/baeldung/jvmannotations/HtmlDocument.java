package com.baeldung.jvmannotations;

public class HtmlDocument implements Document {

    @Override
    public String getType() {
        return "HTML";
    }
}