package com.baeldung.concurrent.runnable;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLoggingTask implements  Runnable{
    private Logger logger = LoggerFactory.getLogger(EventLoggingTask.class);

    @Override
    public void run() {

        String messge="Message read from the event queue";
        logger.info("Message read from event queue is "+messge);

    }
}
