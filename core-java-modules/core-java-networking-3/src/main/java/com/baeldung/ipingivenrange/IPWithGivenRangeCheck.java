package com.baeldung.ipingivenrange;

import java.net.InetAddress;
import java.net.UnknownHostException;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.AddressStringException;

import com.github.jgonian.ipmath.Ipv4;
import com.github.jgonian.ipmath.Ipv4Range;
import com.github.jgonian.ipmath.Ipv6;
import com.github.jgonian.ipmath.Ipv6Range;
import com.googlecode.ipv6.IPv6Address;
import com.googlecode.ipv6.IPv6AddressRange;

public class IPWithGivenRangeCheck {

    // using IPAddress library
    public static boolean checkIPIsInGivenRange(String inputIP, String rangeStartIP, String rangeEndIP) throws AddressStringException {
        IPAddress startIPAddress = new IPAddressString(rangeStartIP).getAddress();
        IPAddress endIPAddress = new IPAddressString(rangeEndIP).getAddress();
        IPAddressSeqRange ipRange = startIPAddress.toSequentialRange(endIPAddress);
        IPAddress inputIPAddress = new IPAddressString(inputIP).toAddress();

        return ipRange.contains(inputIPAddress);
    }

    // using Commons IP Math library for IPv4
    public static boolean checkIPv4IsInRange(String inputIP, String rangeStartIP, String rangeEndIP) {
        Ipv4 startIPAddress = Ipv4.of(rangeStartIP);
        Ipv4 endIPAddress = Ipv4.of(rangeEndIP);
        Ipv4Range ipRange = Ipv4Range.from(startIPAddress)
            .to(endIPAddress);
        Ipv4 inputIPAddress = Ipv4.of(inputIP);

        return ipRange.contains(inputIPAddress);
    }

    // using Commons IP Math library for IPv6
    public static boolean checkIPv6IsInRange(String inputIP, String rangeStartIP, String rangeEndIP) {
        Ipv6 startIPAddress = Ipv6.of(rangeStartIP);
        Ipv6 endIPAddress = Ipv6.of(rangeEndIP);
        Ipv6Range ipRange = Ipv6Range.from(startIPAddress)
            .to(endIPAddress);
        Ipv6 inputIPAddress = Ipv6.of(inputIP);

        return ipRange.contains(inputIPAddress);
    }

    // checking IP is in range by converting it to an integer
    public static boolean checkIPv4IsInRangeByConvertingToInt(String inputIP, String rangeStartIP, String rangeEndIP) throws UnknownHostException {
        long startIPAddress = ipToLongInt(InetAddress.getByName(rangeStartIP));
        long endIPAddress = ipToLongInt(InetAddress.getByName(rangeEndIP));
        long inputIPAddress = ipToLongInt(InetAddress.getByName(inputIP));

        return (inputIPAddress >= startIPAddress && inputIPAddress <= endIPAddress);
    }

    private static long ipToLongInt(InetAddress ipAddress) {
        long resultIP = 0;
        byte[] ipAddressOctets = ipAddress.getAddress();

        for (byte octet : ipAddressOctets) {
            resultIP <<= 8;
            resultIP |= octet & 0xFF;
        }
        return resultIP;
    }

    // using Java IPv6 library (which internally uses two long integers to store ip address)
    public static boolean checkIPv6IsInRangeByIPv6library(String inputIP, String rangeStartIP, String rangeEndIP) {
        IPv6Address startIPAddress = IPv6Address.fromString(rangeStartIP);
        IPv6Address endIPAddress = IPv6Address.fromString(rangeEndIP);
        IPv6AddressRange ipRange = IPv6AddressRange.fromFirstAndLast(startIPAddress, endIPAddress);
        IPv6Address inputIPAddress = IPv6Address.fromString(inputIP);

        return ipRange.contains(inputIPAddress);
    }
}
