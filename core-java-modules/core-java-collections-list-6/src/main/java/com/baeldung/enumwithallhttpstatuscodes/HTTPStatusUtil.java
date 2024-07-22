package com.baeldung.enumwithallhttpstatuscodes;

public class HTTPStatusUtil {
    public static String getStatusDescription(int code) {
        for (HTTPStatus status : HTTPStatus.values()) {
            if (status.getCode() == code) {
                return status.getDescription();
            }
        }
        return "Unknown Status";
    }
}