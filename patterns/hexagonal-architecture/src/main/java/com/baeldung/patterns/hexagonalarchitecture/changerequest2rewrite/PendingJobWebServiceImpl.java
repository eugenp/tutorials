package com.baeldung.patterns.hexagonalarchitecture.changerequest2rewrite;

import java.util.Random;

public class PendingJobWebServiceImpl implements PendingJobService {
    @Override
    public int getPendingJobCount() {
        System.out.println("sending HTTP request...");
        // send HTTP request: GET http://pending-job-web-service.com/count
        // We are returning a random integer for convenience purposes.
        return new Random().nextInt(9999);
    }
}
