package com.baeldung.java9.process;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class ProcessUtils {

    public static String getClassPath() {
        String cp = System.getProperty("java.class.path");
        System.out.println("ClassPath is " + cp);
        return cp;
    }

    public static File getJavaCmd() throws IOException {
        String javaHome = System.getProperty("java.home");
        File javaCmd;
        if (System.getProperty("os.name").startsWith("Win")) {
            javaCmd = new File(javaHome, "bin/java.exe");
        } else {
            javaCmd = new File(javaHome, "bin/java");
        }
        if (javaCmd.canExecute()) {
            return javaCmd;
        } else {
            throw new UnsupportedOperationException(javaCmd.getCanonicalPath() + " is not executable");
        }
    }

    public static String getMainClass() {
        return System.getProperty("sun.java.command");
    }

    public static String getSystemProperties() {
        StringBuilder sb = new StringBuilder();
        System.getProperties().forEach((s1, s2) -> sb.append(s1 + " - " + s2));
        return sb.toString();
    }
}
