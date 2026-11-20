package com.baeldung.junit5.autoclose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DummyClearableResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyClearableResource.class);
    private boolean cleared = false;

    public void clear() {
        LOGGER.info("Clear Dummy Resource");
        cleared = true;
    }

    public boolean isClear() {
        return cleared;
    }
}
