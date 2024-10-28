package com.baeldung.urlipaddress;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

public class GetURLIPAddressUnitTest {

    @Test
    public void givenValidURL_whenGetByInetAddress_shouldReturnAValidIPAddress() throws UnknownHostException {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertTrue(validate(urlipAddress.getByInetAddress("www.example.com")));
    }

    @Test
    public void givenInvalidURL_whenGetByInetAddress_shouldThrowUnknownHostException() {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertThrows(UnknownHostException.class, () -> urlipAddress.getByInetAddress("https://www.example.com"));
    }

    @Test
    public void givenValidURL_whenGetBySocketConnection_shouldReturnAValidIPAddress() throws IOException {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertTrue(validate(urlipAddress.getBySocketConnection("google.com")));
    }

    @Test
    public void givenInvalidURL_whenGetBySocketConnection_shouldThrowUnknownHostException() {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertThrows(UnknownHostException.class, () -> urlipAddress.getBySocketConnection("https://www.example.com"));
    }

    public static boolean validate(final String ip) {
        System.out.println("ip = " + ip);
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }

}
