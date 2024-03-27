package com.baeldung.inetspi.providers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.baeldung.inetspi.Registry;

public class CustomAddressResolverImpl extends InetAddressResolverProvider {

    private static Logger LOGGER = Logger.getLogger(CustomAddressResolverImpl.class.getName());

    private static Registry registry = new Registry();

    @Override
    public InetAddressResolver get(Configuration configuration) {
        LOGGER.info("Using Custom Address Resolver :: " + this.name());
        LOGGER.info("Registry initialised");
        return new InetAddressResolver() {
            @Override
            public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy) throws UnknownHostException {
                return registry.getAddressesfromHost(host);
            }

            @Override
            public String lookupByAddress(byte[] addr) throws UnknownHostException {
                return registry.getHostFromAddress(addr);
            }
        };
    }

    @Override
    public String name() {
        return "CustomInternetAddressResolverImpl";
    }
}
