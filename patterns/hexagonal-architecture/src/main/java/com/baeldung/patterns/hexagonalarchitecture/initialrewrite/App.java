package com.baeldung.patterns.hexagonalarchitecture.initialrewrite;

public class App {
    public static void main(String[] args) {
 
        PendingJobDatabaseImpl pendingJobDatabaseImpl = new PendingJobDatabaseImpl();
        EmailNotificationImpl emailNotificationImpl = new EmailNotificationImpl();
 
        MaintenanceService maintenanceService = new MaintenanceService(pendingJobDatabaseImpl, emailNotificationImpl);
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
