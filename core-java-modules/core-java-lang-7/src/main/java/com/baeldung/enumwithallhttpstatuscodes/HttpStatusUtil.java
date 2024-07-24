package com.baeldung.enumwithallhttpstatuscodes;

public class HttpStatusUtil {
    public static String getStatusDescription(int code) {
        HttpStatus status = HttpStatus.getStatusFromCode(code);
        return (status != null) ? status.getDescription() : "Unknown Status";
    }
}
