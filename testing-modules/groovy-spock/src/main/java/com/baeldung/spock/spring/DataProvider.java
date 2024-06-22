package com.baeldung.spock.spring;

import org.springframework.stereotype.Component;

@Component
public class DataProvider {

    public String fetchData(final String input) {
        return "data for " + input;
    }

}