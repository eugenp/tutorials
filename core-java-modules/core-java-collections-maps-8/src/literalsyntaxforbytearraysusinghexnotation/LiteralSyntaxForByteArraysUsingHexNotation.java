package com.baeldung.literalsyntaxforbytearraysusinghexnotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiteralSyntaxForByteArraysUsingHexNotation {
    private static final Logger logger = LoggerFactory.getLogger(LiteralSyntaxForByteArraysUsingHexNotation.class);

    public static void initializeByteArrayWithDecimal() {
        byte[] byteArray = {10, 20, 30, 40, 50};
        for (byte b : byteArray) {
            logger.info("{}", b);
        }
    }

    public static void initializeByteArrayWithHex() {
        byte[] byteArray = {0x0A, 0x14, 0x1E, 0x28, 0x32};
        for (byte b : byteArray) {
            logger.info(String.format("0x%02X", b));
        }
    }

    public static void main(String[] args) {
        initializeByteArrayWithDecimal();
        initializeByteArrayWithHex();
    }
}
