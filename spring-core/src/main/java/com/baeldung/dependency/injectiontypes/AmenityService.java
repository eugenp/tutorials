package com.baeldung.dependency.injectiontypes;

import org.springframework.stereotype.Service;

@Service
public class AmenityService {

    public String print() {
        return "Hello from Amenity Service";
    }

}
