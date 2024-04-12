package com.baeldung.spring.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.baeldung.spring.model.Modes;

@Component
public class StringToEnumConverter implements Converter<String, Modes> {
    @Override
    public Modes convert(String source) {
        // Remove the try-catch block if we want to handle the exception globally in GlobalControllerExceptionHandler
        try {
            return Modes.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

    }
}
