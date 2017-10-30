package org.baeldung.config;

import org.baeldung.converter.GenericBigDecimalConverter;
import org.baeldung.converter.StringToEnumConverterFactory;
import org.baeldung.converter.StringToEmployeeConverter;
import org.baeldung.web.resolver.HeaderVersionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

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
