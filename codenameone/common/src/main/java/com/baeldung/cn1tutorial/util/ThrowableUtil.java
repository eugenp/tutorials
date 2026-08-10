package com.baeldung.cn1tutorial.util;

/**
 * Formats throwables without relying on desktop-only APIs.
 */
public final class ThrowableUtil {
    /**
     * Utility class; do not instantiate.
     */
    private ThrowableUtil() {
    }

    /**
     * Renders the throwable chain into plain text.
     *
     * @param throwable throwable to render
     * @return full stack trace text
     */
    public static String stackTraceToString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        appendThrowable(builder, throwable, "");
        return builder.toString();
    }

    /**
     * Appends one throwable plus its cause chain.
     *
     * @param builder destination buffer
     * @param throwable throwable to render
     * @param prefix prefix used for nested causes
     */
    private static void appendThrowable(StringBuilder builder, Throwable throwable, String prefix) {
        builder.append(prefix).append(throwable).append('\n');
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace != null) {
            for (int i = 0; i < stackTrace.length; i++) {
                builder.append("    at ").append(stackTrace[i]).append('\n');
            }
        }
        Throwable cause = throwable.getCause();
        if (cause != null && cause != throwable) {
            appendThrowable(builder, cause, "Caused by: ");
        }
    }
}
