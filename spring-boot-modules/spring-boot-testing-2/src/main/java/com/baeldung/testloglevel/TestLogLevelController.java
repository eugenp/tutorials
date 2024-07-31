package com.baeldung.testloglevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.component.OtherComponent;

@RestController
public class TestLogLevelController {

    private static final Logger LOG = LoggerFactory.getLogger(TestLogLevelController.class);

    @Autowired
    private OtherComponent otherComponent;

    @GetMapping("/testLogLevel")
    public String testLogLevel() {
        LOG.trace("This is a TRACE log");
        LOG.debug("This is a DEBUG log");
        LOG.info("This is an INFO log");
        LOG.error("This is an ERROR log");

        otherComponent.processData();

        return "Added some log output to console...";
    }

}
