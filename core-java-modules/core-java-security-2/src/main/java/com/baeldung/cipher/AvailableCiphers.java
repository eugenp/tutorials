package com.baeldung.cipher;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AvailableCiphers {
    public static void main(String[] args) {
        printAllCiphers();
        printCompatibleCiphers();
    }

    private static void printAllCiphers() {
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                System.out.println(service.getAlgorithm());
            }
        }
    }

    private static void printCompatibleCiphers() {
        List<String> algorithms = Arrays.stream(Security.getProviders())
                .flatMap(provider -> provider.getServices().stream())
                .filter(service -> "Cipher".equals(service.getType()))
                .map(Provider.Service::getAlgorithm)
                .collect(Collectors.toList());

        algorithms.forEach(System.out::println);
    }
}
