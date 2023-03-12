package com.baeldung.enummapping.config;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.enummapping.converters.StringToLevelConverter;

@Configuration
public class EnumMappingConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
        registry.addConverter(new StringToLevelConverter());
    }
}