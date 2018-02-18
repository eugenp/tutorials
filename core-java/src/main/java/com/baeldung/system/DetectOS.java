package com.baeldung.system;

import java.util.Locale;

import org.apache.commons.lang3.SystemUtils;

public class DetectOS {

    public String getOperatingSystem() {
        String os = System.getProperty("os.name", "generic")
            .toLowerCase(Locale.ENGLISH);
        if (os.contains("mac"))
            return "Mac";
        else if (os.contains("win"))
            return "Windows";
        else if (os.contains("nux"))
            return "Linux";
        return "Generic";
    }

    public String getOperatingSystemSystemUtils() {
        if (SystemUtils.IS_OS_WINDOWS)
            return "Windows";
        else if (SystemUtils.IS_OS_LINUX)
            return "Linux";
        else if (SystemUtils.IS_OS_MAC)
            return "Mac";
        return "Generic";
    }
}
