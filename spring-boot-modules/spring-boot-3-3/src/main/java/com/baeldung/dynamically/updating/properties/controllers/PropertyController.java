package com.baeldung.dynamically.updating.properties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baeldung.dynamically.updating.properties.services.ExampleBean;
import com.baeldung.dynamically.updating.properties.services.PropertyUpdaterService;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyUpdaterService propertyUpdaterService;

    @Autowired
    private ExampleBean exampleBean;

    @PostMapping("/update")
    public String updateProperty(@RequestParam String key, @RequestParam String value) {
        propertyUpdaterService.updateProperty(key, value);
        return "Property updated. Remember to call the actuator /actuator/refresh";
    }

    @GetMapping("/customProperty")
    public String getCustomProperty() {
        return exampleBean.getCustomProperty();
    }
}