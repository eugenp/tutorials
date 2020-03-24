package com.baeldung.encode;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

class Java7StandardCharsetsEncode {

    String encodeString(String string) {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(string);
        return StandardCharsets.UTF_8.decode(buffer)
            .toString();
    }
}
