package com.baeldung.uniqueint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CRC32;

class StringToUniqueInt {

    private static final Map<String, Integer> lookupMap = new HashMap<>();
    private static final AtomicInteger counter = new AtomicInteger(Integer.MIN_VALUE);

    public static int toIntByHashCode(String value) {
        return value.hashCode();
    }

    public static int toIntByCR32(String value) {
        CRC32 crc32 = new CRC32();
        crc32.update(value.getBytes());
        return (int) crc32.getValue();
    }

    public static int toIntByCharFormula(String value) {
        return value.chars()
            .reduce(17, (a, b) -> a * 13 + (b / a));
    }

    public static int toIntByMD5(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(value.getBytes());
            return ((hash[0] & 0xFF) << 24) | ((hash[1] & 0xFF) << 16) | ((hash[2] & 0xFF) << 8) | (hash[3] & 0xFF);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
    }

    public static int toIntByLookup(String value) {
        Integer found = lookupMap.get(value);
        if (found != null) {
            return found;
        }

        Integer intValue = counter.incrementAndGet();
        lookupMap.put(value, intValue);
        return intValue;
    }
}