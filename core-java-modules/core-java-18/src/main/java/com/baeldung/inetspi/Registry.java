package com.baeldung.inetspi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Registry {
    private final Map<String, List<byte[]>> registry;

    private static final Logger LOGGER = Logger.getLogger(Registry.class.getName());

    public Registry() {
        registry = loadMapWithData();
    }

    public Stream<InetAddress> getAddressesfromHost(String host) throws UnknownHostException {
        LOGGER.info("Performing Forward Lookup for HOST : " + host);
        if (!registry.containsKey(host)) {
            throw new UnknownHostException("Missing Host information in Resolver");
        }
        return registry.get(host)
          .stream()
          .map(add -> constructInetAddress(host, add))
          .filter(Objects::nonNull);
    }

    public String getHostFromAddress(byte[] arr) throws UnknownHostException {
        LOGGER.info("Performing Reverse Lookup for Address : " + Arrays.toString(arr));
        for (Map.Entry<String, List<byte[]>> entry : registry.entrySet()) {
            if (entry.getValue()
              .stream()
              .anyMatch(ba -> Arrays.equals(ba, arr))) {
                return entry.getKey();
            }
        }
        throw new UnknownHostException("Address Not Found");
    }

    private Map<String, List<byte[]>> loadMapWithData() {
        return Map.of("baeldung-local.org", List.of(new byte[] { 1, 2, 3, 4 }));
    }

    private static InetAddress constructInetAddress(String host, byte[] address) {
        try {
            return InetAddress.getByAddress(host, address);
        } catch (UnknownHostException unknownHostException) {
            return null;
        }
    }

}
