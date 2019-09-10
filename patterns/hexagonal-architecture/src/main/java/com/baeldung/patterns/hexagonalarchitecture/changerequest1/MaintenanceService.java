package com.baeldung.patterns.hexagonalarchitecture.changerequest1;

import java.util.Random;
 
public class MaintenanceService {
 
    public static final int MAX_PENDING_JOB_COUNT = 1000;
 
    public void checkPendingJobsAndNotifyIfNecessary() {
        int pendingJobCount = getPendingJobCountFromWebService();
        if (pendingJobCount > MAX_PENDING_JOB_COUNT) {
            sendEmailNotification();
        }
    }
 
    public int getPendingJobCountFromWebService() {
        System.out.println("sending HTTP request...");
        // send HTTP request: GET http://pending-job-web-service.com/count
        // We are returning a random integer for convenience purposes.
        return new Random().nextInt(9999);
    }
 
    public void sendEmailNotification() {
        System.out.println("sending email...");
        // send an email notification to the database team
    }
}
