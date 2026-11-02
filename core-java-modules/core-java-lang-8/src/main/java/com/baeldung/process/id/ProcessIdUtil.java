package com.baeldung.process.id;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;


public final class ProcessIdUtil {

    public static String getProcessIdFromJvmName() {
        String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
        return parsePidFromName(runtimeName);
    }


    public static long getProcessIdFromRuntimeMXBean() {
        return ManagementFactory.getRuntimeMXBean().getPid();
    }


    public static long getProcessIdFromProcessHandle() {
        return ProcessHandle.current().pid();
    }



    public static long getProcessId() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

        if (isJavaVersionOrGreater(10)) {
            return runtimeMxBean.getPid();
        }

        if (isJavaVersionOrGreater(9)) {
            return ProcessHandle.current().pid();
        }

        String runtimeName = runtimeMxBean.getName();
        int index = runtimeName.indexOf('@');
        if (index > 0) {
            return Long.parseLong(runtimeName.substring(0, index));
        }

        throw new IllegalStateException("Could not determine process ID");
    }

    static String parsePidFromName(String jvmName) {
        if (jvmName == null) {
            return null;
        }
        int index = jvmName.indexOf('@');
        if (index < 1) {
            return null;
        }
        return jvmName.substring(0, index);
    }

    private static boolean isJavaVersionOrGreater(int version) {
        String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.")) {
            javaVersion = javaVersion.substring(2);
        }
        int majorVersion = Integer.parseInt(javaVersion.split("\\.")[0]);
        return majorVersion >= version;
    }
}
