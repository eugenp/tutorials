package com.baeldung.naming;

import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Collections;
import java.util.Map;

/**
 * Custom implementation of the {@link HibernatePropertiesCustomizer HibernatePropertiesCustomizer}.
 * We can use it to set custom hibernate properties.
 */
public class HibernateConfig implements HibernatePropertiesCustomizer {

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(MetadataExtractorIntegrator.INSTANCE));
    }
}
