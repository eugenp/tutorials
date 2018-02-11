package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyBeanInjectionService {

    @Autowired
    private PropertyReader propertyReader;

    public String readMessage() {
        return propertyReader.readMessage();
    }
}
