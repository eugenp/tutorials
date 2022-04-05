package com.baeldung.dynamic.autowire;

import org.springframework.stereotype.Service;

@Service("USregionService")
public class USRegionService implements RegionService {
    @Override
    public boolean isServerActive(int serverId) {
        return true;
    }

    @Override
    public String getISOCountryCode() {
        return "US";
    }
}
