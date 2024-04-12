package com.baeldung.typeconversion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.typeconversion.converter.GenericBigDecimalConverter;
import com.baeldung.typeconversion.converter.StringToEmployeeConverter;
import com.baeldung.typeconversion.converter.StringToEnumConverterFactory;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	 
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEmployeeConverter());
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new GenericBigDecimalConverter());
    }
}

