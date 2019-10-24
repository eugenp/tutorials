package com.baeldung.hexagonal.boundary;

import com.baeldung.hexagonal.inner.Location;

public interface IpLocationHandler {

    void handleIpLocation(String ip, Location location);
}
