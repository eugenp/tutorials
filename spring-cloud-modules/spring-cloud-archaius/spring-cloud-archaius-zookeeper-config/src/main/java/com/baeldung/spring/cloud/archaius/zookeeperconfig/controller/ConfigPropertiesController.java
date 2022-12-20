package com.baeldung.spring.cloud.archaius.zookeeperconfig.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

@RestController
public class ConfigPropertiesController {
    
    @Value("${baeldung.archaius.properties.one:not found!}")
    private String propertyOneWithValue;

    @Value("${baeldung.archaius.properties.two:not found!}")
    private String propertyTwoWithValue;

    @Value("${baeldung.archaius.properties.three:not found!}")
    private String propertyThreeWithValue;

    private DynamicStringProperty propertyOneWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.one", "not found!");

    private DynamicStringProperty propertyTwoWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.two", "not found!");
    
    private DynamicStringProperty propertyThreeWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.three", "not found!");

    @GetMapping("/properties-from-dynamic")
    public Map<String, String> getPropertiesFromDynamic() {
        Map<String, String> properties = new HashMap<>();
        properties.put(propertyOneWithDynamic.getName(), propertyOneWithDynamic.get());
        properties.put(propertyTwoWithDynamic.getName(), propertyTwoWithDynamic.get());
        properties.put(propertyThreeWithDynamic.getName(), propertyThreeWithDynamic.get());
        return properties;
    }
    
    @GetMapping("/properties-from-value")
    public Map<String, String> getPropertiesFromValue() {
        Map<String, String> properties = new HashMap<>();
        properties.put("baeldung.archaius.properties.one", propertyOneWithValue);
        properties.put("baeldung.archaius.properties.two", propertyTwoWithValue);
        properties.put("baeldung.archaius.properties.three", propertyThreeWithValue);
        return properties;
    }
}
