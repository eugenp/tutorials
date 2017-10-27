package org.baeldung.spring.service.impl;

import org.baeldung.spring.service.IGeoLocationService;
import org.springframework.stereotype.Service;

@Service
public class GeoLocationService implements IGeoLocationService {

    @Override
    public void saveLocation(String location) {
        System.out.println(String.format("%s Saved Successfully!",location));
    }
}
