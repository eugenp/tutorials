package com.baeldung.dynamic.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomMapFromListDynamicAutowireService {
    private final Map<String, RegionService> servicesByCountryCode;

    @Autowired
    public CustomMapFromListDynamicAutowireService(List<RegionService> regionServices) {
        servicesByCountryCode = regionServices.stream()
                .collect(Collectors.toMap(RegionService::getISOCountryCode, Function.identity()));
    }

    public boolean isServerActive(String isoCountryCode, int serverId) {
        RegionService service = servicesByCountryCode.get(isoCountryCode);

        return service.isServerActive(serverId);
    }
}
