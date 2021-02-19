package com.baeldung.docker.heapsizing;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintXmxXms {

    private static final Logger logger = Logger.getLogger(PrintXmxXms.class.getName());

    public static void main(String[] args) {
        logMemory(logger);
    }

    /**
     * We're reusing this method in HeapSizingApplication, therefore this method was extracted
     * to avoid repetition.
     */
    static void logMemory(Logger logger) {
        float mb = 1024f * 1024f;
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // xmx controls the maximum size of the memory allocation pool,
        // which includes the heap, the garbage collector's survivor space, and other pools.
        float xmx = memoryBean.getHeapMemoryUsage().getMax() / mb;
        float xms = memoryBean.getHeapMemoryUsage().getInit() / mb;
        logger.log(Level.INFO, "Initial Memory (xms) : {0}mb", xms);
        logger.log(Level.INFO, "Max Memory (xmx) : {0}mb", xmx);

        for (MemoryPoolMXBean mp : ManagementFactory.getMemoryPoolMXBeans()) {
            logger.log(Level.INFO, "Pool: {0} (type {1}) = {2}", new Object[]{ mp.getName(), mp.getType(), mp.getUsage().getMax() / mb });
        }
    }
}
