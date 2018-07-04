package com.baeldung.reactive.utils;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class CpuUtils {

    private static OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static Double getUsage() {
        return (operatingSystemMXBean.getSystemCpuLoad() / operatingSystemMXBean.getAvailableProcessors()) * 100;
    }

}
