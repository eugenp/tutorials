package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private BedService bedService;

    @Autowired
    public void setBedService(BedService bedService) {
        this.bedService = bedService;
    }

    public String print() {
        return "Hello from Room Service";
    }

    public BedService getBedService() {
        return bedService;
    }
}
