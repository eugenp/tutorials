package com.baeldung.lombok.with;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KioskDeviceWithUnitTest {

    @Test
    public void whenDeviceInspected_thenClonedAndUpdated() {
        KioskDevice device = new KioskDevice("S-001", false);

        Device inspectedDevice = device.withInspected(true);

        assertNotSame(inspectedDevice, device);
        assertFalse(device.isInspected());
        assertTrue(inspectedDevice.isInspected());
    }
}
