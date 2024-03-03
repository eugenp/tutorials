package com.baeldung.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void log(String message,String url){
        logger.info("Logging Request {} for URI : {}",message,url);
    }
}
