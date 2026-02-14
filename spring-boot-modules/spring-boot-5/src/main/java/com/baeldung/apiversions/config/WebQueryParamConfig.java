package com.baeldung.apiversions.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebQueryParamConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
            .addSupportedVersions("1.0.0", "2.0.0")
            .setDefaultVersion("1.0.0")
            .useQueryParam("version");
    }
}
