package com.baeldung.shutdownhooks.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class Bean2 implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("Shutdown triggered using DisposableBean.");
    }

}
