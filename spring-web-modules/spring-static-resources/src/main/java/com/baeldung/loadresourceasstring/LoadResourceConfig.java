package com.baeldung.loadresourceasstring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadResourceConfig {

    @Bean
    public String resourceString() {
        return ResourceReader.readFileToString("resource.txt");
    }

}
