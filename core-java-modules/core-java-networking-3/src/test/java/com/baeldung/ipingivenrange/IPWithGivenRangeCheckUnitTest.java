package com.baeldung.ipingivenrange;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baeldung.ipingivenrange.IPWithGivenRangeCheck;

class IPWithGivenRangeCheckUnitTest {

    @Test
    void givenIPAddresses_whenValid_thenCheckInputIPIsInTheRange() throws Exception {
        // tests for IPAddress library
        assertTrue(IPWithGivenRangeCheck.checkIPIsInGivenRange("192.220.3.0", "192.210.0.0", "192.255.0.0"));
        assertFalse(IPWithGivenRangeCheck.checkIPIsInGivenRange("192.200.0.0", "192.210.0.0", "192.255.0.0"));

        assertTrue(IPWithGivenRangeCheck.checkIPIsInGivenRange("2001:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));
        assertFalse(IPWithGivenRangeCheck.checkIPIsInGivenRange("2002:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));

        // tests for Common IP Math library
        assertTrue(IPWithGivenRangeCheck.checkIPv4IsInRange("192.220.3.0", "192.210.0.0", "192.255.0.0"));
        assertFalse(IPWithGivenRangeCheck.checkIPv4IsInRange("192.200.0.0", "192.210.0.0", "192.255.0.0"));

        assertTrue(IPWithGivenRangeCheck.checkIPv6IsInRange("2001:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));
        assertFalse(IPWithGivenRangeCheck.checkIPv6IsInRange("2002:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));

        // tests for IPv4 by converting it to an integer and checking if it falls under the specified range.
        assertTrue(IPWithGivenRangeCheck.checkIPv4IsInRangeByConvertingToInt("192.220.3.0", "192.210.0.0", "192.255.0.0"));
        assertFalse(IPWithGivenRangeCheck.checkIPv4IsInRangeByConvertingToInt("192.200.0.0", "192.210.0.0", "192.255.0.0"));

        // tests for Java IPv6 library
        assertTrue(IPWithGivenRangeCheck.checkIPv6IsInRangeByIPv6library("fe80::226:2dff:fefa:dcba", "fe80::226:2dff:fefa:cd1f", "fe80::226:2dff:fefa:ffff"));
        assertFalse(IPWithGivenRangeCheck.checkIPv6IsInRangeByIPv6library("2002:db8:85a3::8a03:a:b", "2001:db8:85a3::8a00:ff:ffff", "2001:db8:85a3::8a2e:370:7334"));
    }
}
