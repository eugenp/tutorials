package com.baeldung.enums.extendenum;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public enum ExtendedStringOperation implements StringOperation {
    MD5_ENCODE("Encoding the given string using the MD5 algorithm.") {
        @Override
        public String apply(String input) {
            return DigestUtils.md5Hex(input);
        }
    },
    BASE64_ENCODE("Encoding the given string using the BASE64 algorithm.") {
        @Override
        public String apply(String input) {
            return new String(new Base64().encode(input.getBytes()));
        }
    };

    private String description;

    ExtendedStringOperation(String description) {
        this.description = description;
    }


    @Override
    public String getDescription() {
        return description;
    }
}