package com.sql.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MySecureBean {

    @Value("${database.password}")
    private String str;

    public String getStr() {
        return str;
    }

}
