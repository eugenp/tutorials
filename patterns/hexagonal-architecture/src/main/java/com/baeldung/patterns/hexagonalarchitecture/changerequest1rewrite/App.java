package com.baeldung.patterns.hexagonalarchitecture.changerequest1rewrite;

public class App {
    public static void main(String[] args) {
 
        PendingJobWebServiceImpl pendingJobWebServiceImpl = new PendingJobWebServiceImpl();
        EmailNotificationImpl emailNotificationImpl = new EmailNotificationImpl();
 
        MaintenanceService maintenanceService = new MaintenanceService(pendingJobWebServiceImpl, emailNotificationImpl);
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
