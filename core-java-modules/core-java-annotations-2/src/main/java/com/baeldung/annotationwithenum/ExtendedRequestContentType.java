package com.baeldung.annotationwithenum;

import java.util.Arrays;

public enum ExtendedRequestContentType {
    JSON(Constants.JSON_VALUE), XML(Constants.XML_VALUE), HTML(Constants.HTML_VALUE);

    ExtendedRequestContentType(String name) {
        if (!name.equals(this.name()))
            throw new IllegalArgumentException();
    }

    public static class Constants {
        public static final String JSON_VALUE = "JSON";
        public static final String XML_VALUE = "XML";
        public static final String HTML_VALUE = "HTML";
    }

    static ExtendedRequestContentType toEnum(String constant) {
        return Arrays.stream(ExtendedRequestContentType.values())
          .filter(contentType -> contentType.name()
            .equals(constant))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
