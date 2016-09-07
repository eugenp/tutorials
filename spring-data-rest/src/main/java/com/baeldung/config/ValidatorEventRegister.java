package com.baeldung.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

@Configuration
public class ValidatorEventRegister implements InitializingBean {

    @Autowired
    ValidatingRepositoryEventListener validatingRepositoryEventListener;

    @Autowired
    private Map<String, Validator> validators;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> events = Arrays.asList("beforeCreate", "afterCreate", "beforeSave", "afterSave", "beforeLinkSave", "afterLinkSave", "beforeDelete", "afterDelete");
        List<String> EVENTS = Collections.unmodifiableList(events);
        
        for (Map.Entry<String, Validator> entry : validators.entrySet()) {
            EVENTS.stream().filter(p -> entry.getKey().startsWith(p)).findFirst().ifPresent(p -> validatingRepositoryEventListener.addValidator(p, entry.getValue()));
        }
    }
}
