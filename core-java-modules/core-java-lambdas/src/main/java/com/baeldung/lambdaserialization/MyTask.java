package com.baeldung.lambdaserialization;

import org.slf4j.*;

import java.io.Serializable;

public class MyTask implements Runnable, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(MyTask.class);
    
    
    public MyTask() {
        
    }
    
    public void run() {
        logger.info("Serialized using class ");
    }
}
