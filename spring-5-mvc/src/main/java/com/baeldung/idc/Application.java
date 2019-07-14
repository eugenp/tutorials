package com.baeldung.idc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.idc")
@PropertySource(value = { "classpath:/custom.properties" }, ignoreResourceNotFound = true)
public class Application {

    public static void main(String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        final Map<String, Object> map = new HashMap<>();
        map.put("spring.config.name", "custom.properties");
        builder.properties(map).run(args);

    }

}
