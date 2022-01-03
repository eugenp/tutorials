package com.baeldung.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OtherComponent {

    private static final Logger LOG = LoggerFactory.getLogger(OtherComponent.class);

    public void processData() {
        LOG.trace("This is a TRACE log from another package");
        LOG.debug("This is a DEBUG log from another package");
        LOG.info("This is an INFO log from another package");
        LOG.error("This is an ERROR log from another package");
    }

}
