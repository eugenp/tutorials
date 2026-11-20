package com.baeldung.ipv4toipv6;

import java.net.InetAddress;
import java.net.UnknownHostException;

class Ipv4ToIpv6Converter {

    String toIpv4MappedIpv6(String ipv4Address) throws UnknownHostException {
        validateIpv4(ipv4Address);
        return "::ffff:" + ipv4Address;
    }

    String toIpv4CompatibleIpv6(String ipv4Address) throws UnknownHostException {
        validateIpv4(ipv4Address);
        return "::" + ipv4Address;
    }

    String toNat64Ipv6(String ipv4Address) throws UnknownHostException {
        validateIpv4(ipv4Address);
        return "64:ff9b::" + ipv4Address;
    }

    String toSixToFourIpv6(String ipv4Address) throws UnknownHostException {
        byte[] bytes = getValidatedIpv4Bytes(ipv4Address);

        return String.format(
            "2002:%02x%02x:%02x%02x::",
            bytes[0] & 0xff,
            bytes[1] & 0xff,
            bytes[2] & 0xff,
            bytes[3] & 0xff
        );
    }

    private void validateIpv4(String ipv4Address) throws UnknownHostException {
        getValidatedIpv4Bytes(ipv4Address);
    }

    private byte[] getValidatedIpv4Bytes(String ipv4Address) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipv4Address);
        byte[] addressBytes = inetAddress.getAddress();

        if (addressBytes.length != 4) {
            throw new IllegalArgumentException("Input must be a valid IPv4 address");
        }

        return addressBytes;
    }
}
