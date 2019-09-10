package com.baeldung.patterns.hexagonalarchitecture.changerequest1;

public class App {
    public static void main(String[] args) {
        MaintenanceService maintenanceService = new MaintenanceService();
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
