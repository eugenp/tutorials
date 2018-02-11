package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterBeanInjectionService {

    private PropertyReader propertyReader;

    public String readMessage() {
        return propertyReader.readMessage();
    }

    @Autowired
    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

}
