package com.baeldung.micronaut.httpfilters.utils;

public class CustomHttpHeaders {

    private CustomHttpHeaders() {
    }

    public static final String X_TRACE_HEADER_KEY = "X-Trace-Enabled";
    public static final String REQUEST_ID_HEADER_KEY = "Request-ID";
    public static final String CUSTOM_HEADER_KEY = "custom-header";
    public static final String PRIVILEGED_USER_HEADER_KEY = "Privileged-User";
}
