package com.baeldung.spring.cloud.consul.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DistributedPropertiesApplication {

    @Value("${my.prop}")
    String value;

    @Autowired
    private MyProperties properties;

    @RequestMapping("/getConfigFromValue")
    public String getConfigFromValue() {
        return value;
    }

    @RequestMapping("/getConfigFromProperty")
    public String getConfigFromProperty() {
        return properties.getProp();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DistributedPropertiesApplication.class).web(true)
            .run(args);
    }
}
