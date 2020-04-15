package com.baeldung.ex.beancreationexception.cause6;

import org.springframework.stereotype.Component;

@Component
public class BeanA {
    private IBeanB dependency;
}