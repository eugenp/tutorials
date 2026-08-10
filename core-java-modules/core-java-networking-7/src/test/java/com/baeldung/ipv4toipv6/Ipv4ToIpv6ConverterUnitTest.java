package com.baeldung.ipv4toipv6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

class Ipv4ToIpv6ConverterUnitTest {

    private final Ipv4ToIpv6Converter converter = new Ipv4ToIpv6Converter();

    @Test
    void whenValidIpv4_thenReturnMappedIpv6() throws Exception {
        String result = converter.toIpv4MappedIpv6("192.168.1.1");
        assertEquals("::ffff:192.168.1.1", result);
    }

    @Test
    void whenValidIpv4_thenReturnCompatibleIpv6() throws Exception {
        String result = converter.toIpv4CompatibleIpv6("192.168.1.1");
        assertEquals("::192.168.1.1", result);
    }

    @Test
    void whenValidIpv4_thenReturnNat64Ipv6() throws Exception {
        String result = converter.toNat64Ipv6("192.168.1.1");
        assertEquals("64:ff9b::192.168.1.1", result);
    }

    @Test
    void whenValidIpv4_thenReturnSixToFourIpv6() throws Exception {
        String result = converter.toSixToFourIpv6("192.168.1.1");
        assertEquals("2002:c0a8:0101::", result);
    }

    @Test
    void whenInvalidIpv4_thenThrowException() {
        assertThrows(UnknownHostException.class, () -> {
            converter.toIpv4MappedIpv6("invalid-ip");
        });
    }
}
