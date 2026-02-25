package com.baeldung.apiversions.queryparam;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
            .addSupportedVersions("1.0", "2.0")
            .setDefaultVersion("1.0")
            .useQueryParam("version");
    }
}
