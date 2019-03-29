package com.baeldung.environmentpostprocessor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

@Order(Ordered.LOWEST_PRECEDENCE)
public class PriceCalculationEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PriceCalculationEnvironmentPostProcessor.class);

    private static final String PROPERTY_PREFIX = "com.baeldung.environmentpostprocessor.";
    private static final String OS_ENV_PROPERTY_CALCUATION_MODE = "calculation_mode";
    private static final String OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE = "gross_calculation_tax_rate";
    private static final String OS_ENV_PROPERTY_CALCUATION_MODE_DEFAULT_VALUE = "NET";
    private static final double OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE = 0;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        PropertySource<?> systemEnvPropertySource = environment.getPropertySources()
            .get(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME);

        Map<String, Object> mapPropertySource = new LinkedHashMap<>();
        if (isActive(systemEnvPropertySource)) {
            populatePropertySource(systemEnvPropertySource, mapPropertySource);
        } else {
            logger.warn("System environment variables [calculation_mode,gross_calculation_tax_rate] not detected, fallback to default value [calcuation_mode={},gross_calcuation_tax_rate={}]", OS_ENV_PROPERTY_CALCUATION_MODE_DEFAULT_VALUE,
                OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE);
            populateDefaultPropertySource(mapPropertySource);
        }

        PropertySource<?> priceCalcuationPropertySource = new MapPropertySource("priceCalcuationPS", mapPropertySource);
        environment.getPropertySources()
            .addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, priceCalcuationPropertySource);

    }

    private void populatePropertySource(PropertySource<?> source, Map<String, Object> target) {
        target.put(key(OS_ENV_PROPERTY_CALCUATION_MODE), source.getProperty(OS_ENV_PROPERTY_CALCUATION_MODE));
        target.put(key(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE), source.getProperty(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE));
    }

    private void populateDefaultPropertySource(Map<String, Object> target) {
        target.put(key(OS_ENV_PROPERTY_CALCUATION_MODE), OS_ENV_PROPERTY_CALCUATION_MODE_DEFAULT_VALUE);
        target.put(key(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE), OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE);
    }

    private String key(String key) {
        return PROPERTY_PREFIX + key.replaceAll("\\_", ".");
    }

    private boolean isActive(PropertySource<?> systemEnvpropertySource) {
        if (systemEnvpropertySource.containsProperty(OS_ENV_PROPERTY_CALCUATION_MODE) && systemEnvpropertySource.containsProperty(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE)) {
            return true;
        } else
            return false;
    }

}
