package com.baeldung.staticvalue.injection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PropertyController {

    @Value("${name}")
    private String name;

    @Value("${name}")
    private static String nameNull;

    private static String namesStatic;

    @Value("${name}")
    public void setNameStatic(String name) {
        PropertyController.namesStatic = name;
    }

    @GetMapping("/properties")
    public List<String> getProperties() {
        return Arrays.asList(this.name, namesStatic, nameNull);
    }
}
