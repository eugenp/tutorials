package com.baeldung.patterns.hexagonalarchitecture.changerequest1rewrite;

public class App {
    public static void main(String[] args) {
        MaintenanceService maintenanceService = new MaintenanceService();
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
