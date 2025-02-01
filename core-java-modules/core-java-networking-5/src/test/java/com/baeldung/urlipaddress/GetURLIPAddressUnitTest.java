package com.baeldung.urlipaddress;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

public class GetURLIPAddressUnitTest {

    @Test
    public void givenValidURL_whenGetByInetAddress_thenReturnAValidIPAddress() throws UnknownHostException {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertTrue(urlipAddress.validate(urlipAddress.getByInetAddress("www.example.com")));
    }

    @Test
    public void givenInvalidURL_whenGetByInetAddress_thenThrowUnknownHostException() {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertThrows(UnknownHostException.class, () -> urlipAddress.getByInetAddress("https://www.example.com"));
    }

    @Test
    public void givenValidURL_whenGetBySocketConnection_thenReturnAValidIPAddress() throws IOException {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertTrue(urlipAddress.validate(urlipAddress.getBySocketConnection("google.com")));
    }

    @Test
    public void givenInvalidURL_whenGetBySocketConnection_thenThrowUnknownHostException() {
        URLIPAddress urlipAddress = new URLIPAddress();
        assertThrows(UnknownHostException.class, () -> urlipAddress.getBySocketConnection("https://www.example.com"));
    }
}
