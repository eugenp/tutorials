package com.baeldung.patterns.hexagonalarchitecture.changerequest2rewrite;

public class App {
    public static void main(String[] args) {
 
        PendingJobWebServiceImpl pendingJobWebServiceImpl = new PendingJobWebServiceImpl();
        SmsNotificationImpl smsNotificationImpl = new SmsNotificationImpl();
 
        MaintenanceService maintenanceService = new MaintenanceService(pendingJobWebServiceImpl, smsNotificationImpl);
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
