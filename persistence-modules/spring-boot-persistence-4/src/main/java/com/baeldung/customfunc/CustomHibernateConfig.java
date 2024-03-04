package com.baeldung.customfunc;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Map;

@Configuration
public class CustomHibernateConfig implements HibernatePropertiesCustomizer {

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.dialect", "com.baeldung.customfunc.CustomH2Dialect");
    }

}