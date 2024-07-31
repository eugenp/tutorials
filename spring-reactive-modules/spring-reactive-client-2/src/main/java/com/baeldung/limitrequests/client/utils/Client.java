package com.baeldung.limitrequests.client.utils;

public interface Client {

    String SEPARATOR = ":";

    static String id(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (Object object : args) {
            builder.append(":")
                .append(object.toString());
        }
        return builder.toString()
            .replaceFirst(SEPARATOR, "");
    }
}
