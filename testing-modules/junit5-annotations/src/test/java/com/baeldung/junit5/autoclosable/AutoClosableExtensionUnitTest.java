package com.baeldung.junit5.autoclosable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

class AutoClosableExtensionUnitTest {

    @AutoClose
    DummyAutoClosableResource resource = new DummyAutoClosableResource();

    @AutoClose("clear")
    DummyClearableResource clearResource = new DummyClearableResource();

    @Test
    void whenUsingDummyAutoClosableResource_thenResourceIsOpen() {
        assertTrue(resource.isOpen());
    }

    @Test
    void whenUsingUsingDummyClearableResource_thenResourceIsNotCleared() {
        assertFalse(clearResource.isClear());
    }

}
