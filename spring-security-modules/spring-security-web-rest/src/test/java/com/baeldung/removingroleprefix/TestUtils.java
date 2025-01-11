package com.baeldung.removingroleprefix;

import org.apache.commons.codec.binary.Base64;

public class TestUtils {
    public static String basicAuthHeader(String user, String password) {
        return "Basic " + Base64.encodeBase64String((user + ":" + password).getBytes());
    }
}
