package com.baeldung.patterns.hexagonalarchitecture.changerequest2;

public class App {
    public static void main(String[] args) {
        MaintenanceService maintenanceService = new MaintenanceService();
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
