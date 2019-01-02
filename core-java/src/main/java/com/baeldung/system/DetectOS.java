package com.baeldung.system;

import org.apache.commons.lang3.SystemUtils;

/**
 * @author zn.wang
 * 检测操作系统
 */
public class DetectOS {

    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        System.out.println("Using System Property: " + os);
        return os;
    }

    public String getOperatingSystemSystemUtils() {
        String os = SystemUtils.OS_NAME;
        System.out.println("Using SystemUtils: " + os);
        return os;
    }
}
