package com.baeldung.patterns.hexagonalarchitecture.changerequest2rewrite;

import java.util.Random;
 
public class PendingJobDatabaseImpl implements PendingJobService {
    @Override
    public int getPendingJobCount() {
        System.out.println("executing SQL query...");
        // execute SQL query: SELECT COUNT(*) FROM PENDING_JOB
        // We are returning a random integer for convenience purposes.
        return new Random().nextInt(9999);
    }
}
