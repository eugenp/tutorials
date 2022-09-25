package com.baeldung.portScanner;

import com.baeldung.portscanner.PortScanner;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

class PortScannerUnitTest {

    PortScanner portScanner = new PortScanner();
    private static int nbrPortMax = 1000; // Max is 65535, number of available ports

    @Test public void when_Run_then_lunchPortScan() {
        try {
            portScanner.runPortScan(nbrPortMax);
        } catch (Exception ex) {
            fail("Exception: " + ex);
        }
    }
}
