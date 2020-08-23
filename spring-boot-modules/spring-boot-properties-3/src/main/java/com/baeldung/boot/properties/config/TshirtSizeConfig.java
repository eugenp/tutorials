package com.baeldung.boot.properties.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "t-shirt-size")
public class TshirtSizeConfig {

    private final Map<String, Integer> simpleMapping;

    private final Map<String, Map<String, Integer>> complexMapping;


    public TshirtSizeConfig(Map<String, Integer> simpleMapping, Map<String, Map<String, Integer>> complexMapping) {
        this.simpleMapping = simpleMapping;
        this.complexMapping = complexMapping;
    }

    public Map<String, Integer> getSimpleMapping() {
        return simpleMapping;
    }

    public Map<String, Map<String, Integer>> getComplexMapping() {
        return complexMapping;
    }
}
