package com.baeldung.boot.config;

import java.util.List;

import com.baeldung.boot.converter.GenericBigDecimalConverter;
import com.baeldung.boot.converter.StringToAbstractEntityConverterFactory;
import com.baeldung.boot.converter.StringToEmployeeConverter;
import com.baeldung.boot.converter.StringToEnumConverter;
import com.baeldung.boot.web.resolver.HeaderVersionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderVersionArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEmployeeConverter());
        registry.addConverter(new StringToEnumConverter());
        registry.addConverterFactory(new StringToAbstractEntityConverterFactory());
        registry.addConverter(new GenericBigDecimalConverter());
    }
}
