package com.baeldung.loki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final Logger LOG = LoggerFactory.getLogger(DemoService.class);

    public void log() {
        LOG.info("DemoService.log invoked");
    }
}
