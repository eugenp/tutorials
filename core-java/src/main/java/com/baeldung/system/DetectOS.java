package com.baeldung.system;

import java.util.Locale;

import org.apache.commons.lang3.SystemUtils;

public class DetectOS {

    private static final DetectOS INSTANCE = new DetectOS();
    private static OperatingSystem detectedOS;

    private DetectOS() {

    }

    public enum OperatingSystem {
        Windows, MacOS, Linux, Other
    }

    public static DetectOS getInstance() {
        return INSTANCE;
    }

    public OperatingSystem getOperatingSystem() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic")
                .toLowerCase(Locale.ENGLISH);
            if ((OS.indexOf("mac") >= 0)) {
                detectedOS = OperatingSystem.MacOS;
            } else if (OS.indexOf("win") >= 0) {
                detectedOS = OperatingSystem.Windows;
            } else if (OS.indexOf("nux") >= 0) {
                detectedOS = OperatingSystem.Linux;
            } else {
                detectedOS = OperatingSystem.Other;
            }
        }
        return detectedOS;
    }

    public OperatingSystem getOperatingSystemSystemUtils() {
        if (SystemUtils.IS_OS_WINDOWS)
            return OperatingSystem.Windows;
        else if (SystemUtils.IS_OS_LINUX)
            return OperatingSystem.Linux;
        else if (SystemUtils.IS_OS_MAC)
            return OperatingSystem.MacOS;
        return OperatingSystem.Other;
    }
}
