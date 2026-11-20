package com.baeldung.cn1tutorial.service;

import com.codename1.io.Log;
import com.codename1.system.NativeLookup;
import net.informaticalibera.cn1.nativelogreader.NativeLogs;
import net.informaticalibera.cn1.nativelogreader.NativeLogsReader;

/**
 * Small adapter around the Native Logs Reader CN1Lib.
 * <p>
 * The library initialization clears and restarts the native log buffer. Keeping that call here,
 * guarded by {@link #initialized}, prevents read operations from accidentally erasing the logs.
 */
public final class NativeLogSupport {
    private static boolean initialized;
    private static Boolean supported;

    /**
     * Utility class; do not instantiate.
     */
    private NativeLogSupport() {
    }

    /**
     * Checks the native interface directly instead of guessing from platform-name strings.
     *
     * @return {@code true} when the current port provides the native log reader
     */
    public static boolean isSupported() {
        if (supported != null) {
            return supported.booleanValue();
        }
        try {
            NativeLogsReader reader = NativeLookup.create(NativeLogsReader.class);
            supported = Boolean.valueOf(reader != null && reader.isSupported());
            return supported.booleanValue();
        } catch (RuntimeException ex) {
            Log.e(ex);
            supported = Boolean.FALSE;
            return false;
        }
    }

    /**
     * Initializes native log capture once for the app process.
     */
    public static void initOnce() {
        if (initialized || !isSupported()) {
            return;
        }
        try {
            NativeLogs.initNativeLogs();
            initialized = true;
        } catch (RuntimeException ex) {
            Log.e(ex);
        }
    }

    /**
     * Reads native logs without reinitializing the native log backend.
     *
     * @return native log text, or {@code null} when unsupported
     */
    public static String readLogs() {
        if (!isSupported()) {
            return null;
        }
        initOnce();
        if (!initialized) {
            return "";
        }
        try {
            return NativeLogs.getNativeLogs();
        } catch (RuntimeException ex) {
            Log.e(ex);
            return "";
        }
    }

    /**
     * Clears the native log buffer when available.
     */
    public static void clearLogs() {
        if (!isSupported()) {
            return;
        }
        initOnce();
        if (!initialized) {
            return;
        }
        try {
            NativeLogs.clearNativeLogs();
        } catch (RuntimeException ex) {
            Log.e(ex);
        }
    }
}
