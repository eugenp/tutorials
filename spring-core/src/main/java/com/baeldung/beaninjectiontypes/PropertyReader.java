package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Value;

public class PropertyReader {

        @Value(value = "${baeldung.message}")
        private String property;

        String readMessage() {
                return property;
        }
}
