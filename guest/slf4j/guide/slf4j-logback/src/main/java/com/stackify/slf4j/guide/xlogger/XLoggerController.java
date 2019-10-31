package com.stackify.slf4j.guide.xlogger;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XLoggerController {

    private XLogger logger = XLoggerFactory.getXLogger(XLoggerController.class);

    @GetMapping("/slf4j-guide-xlogger-request")
    public Integer clientXLoggerRequest(@RequestParam("queryParam") Integer queryParam) {
        logger.info("Starting process");
        logger.entry(queryParam);
        Integer rest = 0;
        try {
            rest = queryParam % 3;
        } catch (RuntimeException anyException) {
            logger.catching(anyException);
        }
        logger.exit(rest);
        return rest;
    }

}
