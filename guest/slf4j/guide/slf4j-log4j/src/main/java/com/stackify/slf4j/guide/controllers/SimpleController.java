package com.stackify.slf4j.guide.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    Logger logger = LoggerFactory.getLogger(SimpleController.class);
    
    @GetMapping("/slf4j-guide-request")
    public String processList(List<String> list) {
        logger.info("Client requested process the following list: {}", list);
        try {
            logger.debug("Starting process");
            // ...processing list here...
            Thread.sleep(500);
        } catch (RuntimeException | InterruptedException e) {
            logger.error("There was an issue processing the list.", e);
        } finally {
            logger.info("Finished processing");
        }
        return "done";
    }
    
    @GetMapping("/slf4j-guide-mdc-request")
    public String clientMDCRequest(@RequestHeader String clientId) throws InterruptedException {
        MDC.put("clientId", clientId);
        logger.info("Client {} has made a request", clientId);
        logger.info("Starting request");
        Thread.sleep(500);
        logger.info("Finished request");
        MDC.clear();
        return "finished";
    }
}
