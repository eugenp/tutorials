package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstructorBeanInjectionService {

        PropertyReader propertyReader;

        public String readMessage() {
                return propertyReader.readMessage();
        }

        @Autowired
        public ConstructorBeanInjectionService(PropertyReader propertyReader) {
                this.propertyReader = propertyReader;
        }
}
