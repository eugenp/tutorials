package com.baeldung.dynamic.autowire;

import org.springframework.stereotype.Service;

@Service("usa")
public class USARegionService implements RegionService {
    @Override
    public boolean isServerActive(int serverId) {
        return true;
    }

    @Override
    public String getCountryCode() {
        return "usa";
    }
}
