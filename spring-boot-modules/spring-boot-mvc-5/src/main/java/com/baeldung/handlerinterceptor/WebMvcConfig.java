package com.baeldung.handlerinterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final FeatureFlagService featureFlagService;

    public WebMvcConfig(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DeliveryChargeInterceptor(featureFlagService))
            .addPathPatterns("/delivery-charges/**");
    }
}
