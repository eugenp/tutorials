package com.baeldung.patterns.hexagonalarchitecture.initialrewrite;

public class App {
    public static void main(String[] args) {
        MaintenanceService maintenanceService = new MaintenanceService();
        maintenanceService.checkPendingJobsAndNotifyIfNecessary();
    }
}
