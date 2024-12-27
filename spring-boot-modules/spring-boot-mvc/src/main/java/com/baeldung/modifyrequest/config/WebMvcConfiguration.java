package com.baeldung.modifyrequest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.modifyrequest.interceptor.EscapeHtmlRequestInterceptor;

@Configuration
@Profile("interceptorExample")
public class WebMvcConfiguration implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("addInterceptors() called");
        registry.addInterceptor(new EscapeHtmlRequestInterceptor())
            .addPathPatterns("/save");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

