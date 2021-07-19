package com.baeldung.dynamic.autowire;

import org.springframework.stereotype.Service;

@Service("GBregionService")
public class GBRegionService implements RegionService {
    @Override
    public boolean isServerActive(int serverId) {
        return false;
    }

    @Override
    public String getISOCountryCode() {
        return "GB";
    }
}
