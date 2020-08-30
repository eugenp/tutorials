package com.baeldung.properties.json;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {JsonProperties.class, CustomJsonProperties.class})
public class ConfigPropertiesDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigPropertiesDemoApplication.class).initializers(new JsonPropertyContextInitializer())
                .run();
    }

}
