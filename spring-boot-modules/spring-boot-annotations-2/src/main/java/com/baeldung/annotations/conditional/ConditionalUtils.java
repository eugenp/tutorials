package com.baeldung.annotations.conditional;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.system.JavaVersion;

public class ConditionalUtils {

    public static boolean isWindows() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    public static boolean isJava17() {
        return JavaVersion.getJavaVersion().equals(JavaVersion.SEVENTEEN);
    }

    public static boolean isJava21() {
        return JavaVersion.getJavaVersion().equals(JavaVersion.TWENTY_ONE);
    }

}
