package com.baeldung.system;

import org.apache.commons.lang3.SystemUtils;

public class DetectOS {

    public String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public String getOperatingSystemSystemUtils() {
        return SystemUtils.OS_NAME;
    }
}
