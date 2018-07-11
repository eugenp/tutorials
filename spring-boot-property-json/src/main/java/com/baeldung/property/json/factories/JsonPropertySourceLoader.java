package com.baeldung.property.json.factories;

import com.baeldung.property.json.processor.JsonMapProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonPropertySourceLoader implements PropertySourceLoader {

    @Override
    public String[] getFileExtensions() {
        return new String[] {"json"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        Map<String, Object> originalMap = new ObjectMapper().readValue(resource.getInputStream(), Map.class);
        Map<String, Object> flattenedMap = JsonMapProcessor.getFlattenedMap(originalMap);
        List<PropertySource<?>> propertySources = new ArrayList<>(1);
        propertySources.add(new MapPropertySource(name, flattenedMap));
        return propertySources;
    }
}
