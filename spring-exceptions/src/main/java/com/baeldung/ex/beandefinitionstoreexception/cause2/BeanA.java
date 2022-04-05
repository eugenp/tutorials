package com.baeldung.ex.beandefinitionstoreexception.cause2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanA {

    @Value("${some.property2}")
    private String someProperty2;

}
