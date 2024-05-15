package com.baeldung.connectiondetails.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZipkinDemoController {

    Logger logger = LoggerFactory.getLogger(ZipkinDemoController.class);

    @GetMapping("/zipkin/test")
    public @ResponseBody String testMethod() {
        logger.info("This is a test");
        return "This is a test";
    }
}
