package com.baeldung.hexagonal;

import com.baeldung.hexagonal.boundary.IpLocationHandler;
import com.baeldung.hexagonal.boundary.IpProvider;
import com.baeldung.hexagonal.inner.Location;
import com.baeldung.hexagonal.inner.LocationService;

public class HexagonApplication {

    private LocationService locationService;
    private IpProvider ipProvider;
    private IpLocationHandler ipLocationHandler;

    public HexagonApplication(LocationService locationService, IpProvider ipProvider, IpLocationHandler ipLocationHandler) {
        this.locationService = locationService;
        this.ipProvider = ipProvider;
        this.ipLocationHandler = ipLocationHandler;
    }

    public void handleIp() {
        String ip = ipProvider.ip();
        Location location = locationService.location(ip);
        ipLocationHandler.handleIpLocation(ip, location);
    }
}
