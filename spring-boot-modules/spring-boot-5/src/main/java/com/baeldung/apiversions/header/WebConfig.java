package com.baeldung.apiversions.header;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.addSupportedVersions("1.0", "2.0")
            .setDefaultVersion("2.0")
            .useRequestHeader("X-API-Version");
    }
}
