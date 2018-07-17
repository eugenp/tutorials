package org.baeldung.boot.config;

import org.baeldung.boot.converter.GenericBigDecimalConverter;
import org.baeldung.boot.converter.StringToEmployeeConverter;
import org.baeldung.boot.converter.StringToEnumConverterFactory;
import org.baeldung.boot.web.resolver.HeaderVersionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderVersionArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEmployeeConverter());
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new GenericBigDecimalConverter());
    }
}
