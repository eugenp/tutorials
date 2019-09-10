package com.baeldung.patterns.hexagonalarchitecture.initial;

import java.util.Random;

public class MaintenanceService {
 
    public static final int MAX_PENDING_JOB_COUNT = 1000;
 
    public void checkPendingJobsAndNotifyIfNecessary() {
        int pendingJobCount = getPendingJobCountFromDatabase();
        if (pendingJobCount > MAX_PENDING_JOB_COUNT) {
            sendEmailNotification();
        }
    }
 
    public int getPendingJobCountFromDatabase() {
        System.out.println("executing SQL query...");
        // execute SQL query: SELECT COUNT(*) FROM PENDING_JOB
        // We are returning a random integer for convenience purposes.
        return new Random().nextInt(9999);
    }
 
    public void sendEmailNotification() {
        System.out.println("sending email...");
        // send an email notification to the database team
    }
}
