package com.baeldung.encode;

import java.nio.charset.StandardCharsets;

class CoreJavaEncode {

    String encodeString(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
