package com.baeldung.reactive.util;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class CpuUtils {

    private static OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    static Double getUsage() {
        return (operatingSystemMXBean.getCpuLoad() / operatingSystemMXBean.getAvailableProcessors()) * 100;
    }

}
