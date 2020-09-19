package com.baeldung.cipher;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AvailableCiphersUnitTest {
    private final Logger logger = LoggerFactory.getLogger(AvailableCiphersUnitTest.class);

    @Test
    public void whenGetServices_thenGetAllCipherAlgorithms() {
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                logger.info(service.getAlgorithm());
            }
        }
    }

    @Test
    public void whenGetServicesWithFilter_thenGetAllCompatibleCipherAlgorithms() {
        List<String> algorithms = Arrays.stream(Security.getProviders())
                .flatMap(provider -> provider.getServices().stream())
                .filter(service -> "Cipher".equals(service.getType()))
                .map(Provider.Service::getAlgorithm)
                .collect(Collectors.toList());

        algorithms.forEach(logger::info);
    }
}
