package com.baeldung.junit5.autoclose;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

class AutoCloseExtensionUnitTest {

    @AutoClose
    DummyAutoCloseableResource resource = new DummyAutoCloseableResource();

    @AutoClose("clear")
    DummyClearableResource clearResource = new DummyClearableResource();

    @Test
    void whenUsingDummyAutoCloseableResource_thenResourceIsOpen() {
        assertTrue(resource.isOpen());
    }

    @Test
    void whenUsingUsingDummyClearableResource_thenResourceIsNotCleared() {
        assertFalse(clearResource.isClear());
    }

}
