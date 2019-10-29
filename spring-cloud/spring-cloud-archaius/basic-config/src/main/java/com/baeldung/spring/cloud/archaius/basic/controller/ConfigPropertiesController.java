package com.baeldung.spring.cloud.archaius.basic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.config.DynamicIntProperty;
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

    @Value("${baeldung.archaius.properties.four:not found!}")
    private String propertyFourWithValue;

    private DynamicStringProperty propertyOneWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.one", "not found!");

    private DynamicStringProperty propertyTwoWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.two", "not found!");

    private DynamicStringProperty propertyThreeWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.three", "not found!");

    private DynamicStringProperty propertyFourWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.properties.four", "not found!");

    private DynamicIntProperty intPropertyWithDynamic = DynamicPropertyFactory.getInstance()
        .getIntProperty("baeldung.archaius.properties.int", 0);

    @GetMapping("/properties-from-value")
    public Map<String, String> getPropertiesFromValue() {
        Map<String, String> properties = new HashMap<>();
        properties.put("baeldung.archaius.properties.one", propertyOneWithValue);
        properties.put("baeldung.archaius.properties.two", propertyTwoWithValue);
        properties.put("baeldung.archaius.properties.three", propertyThreeWithValue);
        properties.put("baeldung.archaius.properties.four", propertyFourWithValue);
        return properties;
    }

    @GetMapping("/properties-from-dynamic")
    public Map<String, String> getPropertiesFromDynamic() {
        Map<String, String> properties = new HashMap<>();
        properties.put(propertyOneWithDynamic.getName(), propertyOneWithDynamic.get());
        properties.put(propertyTwoWithDynamic.getName(), propertyTwoWithDynamic.get());
        properties.put(propertyThreeWithDynamic.getName(), propertyThreeWithDynamic.get());
        properties.put(propertyFourWithDynamic.getName(), propertyFourWithDynamic.get());
        return properties;
    }

    @GetMapping("/int-property")
    public Map<String, Integer> getIntPropertyFromDynamic() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put(intPropertyWithDynamic.getName(), intPropertyWithDynamic.get());
        return properties;
    }
}
