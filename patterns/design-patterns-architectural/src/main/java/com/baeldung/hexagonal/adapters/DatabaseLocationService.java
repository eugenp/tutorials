package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.inner.Location;
import com.baeldung.hexagonal.inner.LocationService;
import com.baeldung.hexagonal.outer.IpLocation;
import com.baeldung.hexagonal.outer.IpLocations;

import java.util.Optional;

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
        Optional<IpLocation> location = locations.findByIp(ip);
        return location.map(this::map).orElse(null);
    }
}
