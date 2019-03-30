package com.baeldung.hexagonal.core;

import java.util.UUID;

public class OrderUtils {    
    static public String generateOrderId() {
        return UUID.randomUUID().toString();
    }    
}
