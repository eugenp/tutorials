package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.inner.Location;
import com.baeldung.hexagonal.inner.LocationService;
import com.baeldung.hexagonal.outer.IpLocations;

import java.util.Scanner;

public class IpGeocoder {

    private LocationService locationService;

    public IpGeocoder(LocationService locationService) {
        this.locationService = locationService;
    }

    public void handleIp() {
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();
        Location location = locationService.location(ip);
        System.out.println(ip + " location is " + location);
    }

    public static void main(String[] args) {
        LocationService ls = new DatabaseLocationService(new IpLocations());
        IpGeocoder ipGeocoder = new IpGeocoder(ls);
        ipGeocoder.handleIp();
    }

}
