package com.baeldung.dynamic.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InterfaceDynamicAutowireService {
    private final Map<String, RegionService> servicesByCountryCode;

    @Autowired
    public InterfaceDynamicAutowireService(List<RegionService> regionServices) {
        servicesByCountryCode = regionServices.stream()
                .collect(Collectors.toMap(RegionService::getCountryCode, Function.identity()));
    }

    public boolean isServerActive(String countryCode, int serverId) {
        RegionService service = servicesByCountryCode.get(countryCode);

        return service.isServerActive(serverId);
    }
}
