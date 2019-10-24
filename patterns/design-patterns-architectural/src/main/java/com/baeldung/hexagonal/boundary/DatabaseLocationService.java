package com.baeldung.hexagonal.boundary;

import com.baeldung.hexagonal.inner.Location;
import com.baeldung.hexagonal.inner.LocationService;
import com.baeldung.hexagonal.outer.IpLocation;
import com.baeldung.hexagonal.outer.IpLocations;

public class DatabaseLocationService implements LocationService {

    private IpLocations locations;

    public DatabaseLocationService(IpLocations locations) {
        this.locations = locations;
    }

    private Location map(IpLocation location) {
        return new Location(location.getLatitude(), location.getLongitude());
    }

    @Override
    public Location location(String ip) {
        IpLocation location = locations.byIp(ip);
        return location != null ? map(location) : null;
    }
}
