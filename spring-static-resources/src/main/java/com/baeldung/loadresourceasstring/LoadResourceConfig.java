package com.baeldung.loadresourceasstring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


@Configuration
public class LoadResourceConfig {

    @Bean
    public Converter<String, String> resourceReader() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return path -> {
            Resource resource = resourceLoader.getResource(path);
            return ResourceReader.asString(resource);
        };
    }

    @Bean
    public String resourceString() {
        return ResourceReader.readFileToString("resource.txt");
    }

}
