package com.baeldung.portScanner;

import com.baeldung.portscanner.PortScanner;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PortScannerUnitTest {

    private static final int nbrPortMax = 1000; // Max is 65535, number of available ports
    private static final String ip = "127.0.0.1";
    PortScanner portScanner = new PortScanner();

    @Test public void when_Run_then_lunchPortScan() throws IOException {
        portScanner.runPortScan(ip, nbrPortMax);
    }
}
