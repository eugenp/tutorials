package com.baeldung.stringmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.MapBindingResult;

import java.util.Map;

@Service
public class MapService {
    private final MapValidator mapValidator;

    @Autowired
    public MapService(MapValidator mapValidator) {
        this.mapValidator = mapValidator;
    }

    public void process(Map<String, String> inputMap) {
        // Wrap the map in a binding structure for validation
        MapBindingResult errors = new MapBindingResult(inputMap, "inputMap");

        // Run validation
        mapValidator.validate(inputMap, errors);

        // Handle validation errors
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Validation failed: " + errors.getAllErrors());
        }
        // Business logic goes here...
    }
}