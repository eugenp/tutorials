package com.baeldung.customstatefulvalidation.validators;

import com.baeldung.customstatefulvalidation.configuration.TenantChannels;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class AvailableChannelValidator implements ConstraintValidator<AvailableChannel, String> {

    @Autowired
    private TenantChannels tenantChannels;

    private Set<String> channels;

    @Override
    public void initialize(AvailableChannel constraintAnnotation) {
        channels = Arrays.stream(tenantChannels.getChannels()).collect(toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return channels.contains(value);
    }
}