package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BedService {

    @Autowired
    private AmenityService amenityService;

    public String print() {
        return "Hello from Bed Service";
    }

    public AmenityService getAmenityService() {
        return amenityService;
    }
}
