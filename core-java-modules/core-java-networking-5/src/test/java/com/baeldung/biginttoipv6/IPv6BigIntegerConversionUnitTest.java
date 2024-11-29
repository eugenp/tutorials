package com.baeldung.biginttoipv6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class IPv6BigIntegerConversionUnitTest {

    @Test
    void givenIpv6_whenPerformConversion_thenOutputBigInteger() throws UnknownHostException {
        String ipv6 = "2001:0:4137:9e76:34b7:2e31:3f57:fd9a";
        BigInteger expected = new BigInteger("42540488182159607633435240198452018586");

        InetAddress inetAddress = InetAddress.getByName(ipv6);
        byte[] addressBytes = inetAddress.getAddress();

        if (addressBytes.length != 16) {
            throw new IllegalArgumentException("Not a valid IPv6 address");
        }
        BigInteger result = new BigInteger(1, addressBytes);

        assertEquals(expected, result, "IPv6 to BigInteger conversion failed");
    }

    @Test
    void givenBigInteger_whenPerformConversion_thenOutputIpv6() throws UnknownHostException {
        BigInteger bigInteger = new BigInteger("42540488182159607633435240198452018586");
        String ipv6 = "2001::4137:9e76:34b7:2e31:3f57:fd9a";
        byte[] addressBytes = bigInteger.toByteArray();

        if (addressBytes.length > 16) {
            addressBytes = Arrays.copyOfRange(addressBytes, addressBytes.length - 16, addressBytes.length);
        } else if (addressBytes.length < 16) {
            byte[] padded = new byte[16];
            System.arraycopy(addressBytes, 0, padded, 16 - addressBytes.length, addressBytes.length);
            addressBytes = padded;
        }

        InetAddress inetAddress = InetAddress.getByAddress(addressBytes);
        String result = compressIPv6(inetAddress.getHostAddress());

        assertEquals(ipv6, result, "BigInteger to IPv6 conversion failed");
    }

    private static String compressIPv6(String ipv6) {
        return ipv6.replaceAll("(^|:)0+(?!$)", ":")
            .replaceFirst(":(:)+", "::");
    }
}

