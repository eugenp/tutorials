package com.baeldung.junit5.autoclose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DummyAutoCloseableResource implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyAutoCloseableResource.class);
    private boolean open = true;

    @Override
    public void close() {
        LOGGER.info("Closing Dummy Resource");
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}
