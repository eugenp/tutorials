package com.baeldung.encode;

import org.apache.commons.codec.binary.StringUtils;

class ApacheCommonsCodecEncode {

    String encodeString(String string) {
        byte[] bytes = StringUtils.getBytesUtf8(string);
        return StringUtils.newStringUtf8(bytes);
    }
}
