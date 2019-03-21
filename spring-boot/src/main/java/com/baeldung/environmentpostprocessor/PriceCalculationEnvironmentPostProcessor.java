package com.baeldung.environmentpostprocessor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

public class PriceCalculationEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(PriceCalculationEnvironmentPostProcessor.class);

    public static final int DEFAULT_ORDER = Ordered.LOWEST_PRECEDENCE;
    private int order = DEFAULT_ORDER;
    private static final String PROPERTY_PREFIX = "com.baeldung.environmentpostprocessor.";
    private static final String OS_ENV_PROPERTY_CALCUATION_MODE = "calculation_mode";
    private static final String OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE = "gross_calculation_tax_rate";
    private static final String OS_ENV_PROPERTY_CALCUATION_MODE_DEFAULT_VALUE = "NET";
    private static final double OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE = 0;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        PropertySource<?> systemEnvironmentPropertySource = environment.getPropertySources()
            .get(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME);

        Map<String, Object> priceCalculationConfiguration = new LinkedHashMap<>();
        if (isActive(systemEnvironmentPropertySource)) {
            priceCalculationConfiguration.put(key(OS_ENV_PROPERTY_CALCUATION_MODE), systemEnvironmentPropertySource.getProperty(OS_ENV_PROPERTY_CALCUATION_MODE));
            priceCalculationConfiguration.put(key(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE), systemEnvironmentPropertySource.getProperty(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE));
        } else {
            logger.warn("System environment variables [calculation_mode,gross_calculation_tax_rate] not detected, fallback to default value [calcuation_mode={},gross_calcuation_tax_rate={}]", OS_ENV_PROPERTY_CALCUATION_MODE_DEFAULT_VALUE,
                OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE);
            priceCalculationConfiguration.put(key(OS_ENV_PROPERTY_CALCUATION_MODE), OS_ENV_PROPERTY_CALCUATION_MODE_DEFAULT_VALUE);
            priceCalculationConfiguration.put(key(OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE), OS_ENV_PROPERTY_GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE);
        }

        PropertySource<?> priceCalcuationPropertySource = new MapPropertySource("priceCalcuationPS", priceCalculationConfiguration);
        environment.getPropertySources()
            .addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, priceCalcuationPropertySource);

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

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

}
