package com.baeldung.shutdownhooks.beans;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class Bean1 {

    @PreDestroy
    public void destroy() {
        System.out.println("Shutdown triggered using @PreDestroy.");
    }
}
