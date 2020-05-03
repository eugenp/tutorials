package com.baeldung.dynamic.autowire;

import org.springframework.stereotype.Service;

@Service("uk")
public class UkRegionService implements RegionService {
    @Override
    public boolean isServerActive(int serverId) {
        return false;
    }

    @Override
    public String getCountryCode() {
        return "uk";
    }
}
