package com.baeldung.inetspi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Stream;

import com.baeldung.inetspi.providers.CustomAddressResolverImpl;

public class InetAddressSPI {
    public String usingGetByName(String host) throws UnknownHostException {
        InetAddress inetAddress =  InetAddress.getByName(host);
        return inetAddress.getHostAddress();
    }

    public String[] usingGetAllByName(String host) throws UnknownHostException {
        InetAddress[] inetAddresses =  InetAddress.getAllByName(host);
        return Arrays.stream(inetAddresses).map(InetAddress::getHostAddress).toArray(String[]::new);
    }

    public String usingGetByIp(byte[] ip) throws UnknownHostException {
        InetAddress inetAddress =  InetAddress.getByAddress(ip);

        return inetAddress.getHostName();
    }

    public String usingGetByIpAndReturnsCannonName(byte[] ip) throws UnknownHostException {
        InetAddress inetAddress =  InetAddress.getByAddress(ip);

        return inetAddress.getCanonicalHostName();
    }

    public String getHostUsingCustomImpl(byte[] ip) throws UnknownHostException {

        CustomAddressResolverImpl imp = new CustomAddressResolverImpl();
        return imp.get(null).lookupByAddress(ip);
    }

    public Stream<InetAddress> getIpUsingCustomImpl(String host) throws UnknownHostException {

        CustomAddressResolverImpl imp = new CustomAddressResolverImpl();
        return imp.get(null).lookupByName(host, null);
    }
}
