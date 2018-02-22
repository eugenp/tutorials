package com.baeldung.system;

import org.apache.commons.lang3.SystemUtils;

public class DetectOS {

    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        System.out.println(os);
        return os;
    }

    public String getOperatingSystemSystemUtils() {
        String os = SystemUtils.OS_NAME;
        System.out.println(os);
        return os;
    }
}
