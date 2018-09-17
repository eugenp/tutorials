package com.baeldung.heapdump;

import com.sun.management.HotSpotDiagnosticMXBean;

import javax.management.MBeanServer;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;

public class HeapDump {

    public static void dumpHeap(String filePath, boolean live) throws IOException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(
          server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
        mxBean.dumpHeap(filePath, live);
    }

    public static void main(String[] args) throws IOException {
        String file = Paths.get("dump.hprof").toFile().getPath();

        dumpHeap(file, true);
    }
}
