package com.baeldung.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.stackify.services.MyService;

public class MyServiceTest {
    
    private static final Logger logger = LogManager.getLogger(MyServiceTest.class);

    @Test
    public void testService(){
        MyService myService = new MyService();
        myService.calculate();
        logger.error("aaa");
    }
    
}
