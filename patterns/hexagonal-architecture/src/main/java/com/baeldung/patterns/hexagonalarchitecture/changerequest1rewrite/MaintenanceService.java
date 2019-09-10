package com.baeldung.patterns.hexagonalarchitecture.changerequest1rewrite;

public class MaintenanceService {
 
    public static final int MAX_PENDING_JOB_COUNT = 1000;
 
    private PendingJobService pendingJobService;
    private NotificationService notificationService;
 
    public MaintenanceService(
            PendingJobService pendingJobService,
            NotificationService notificationService) {
        this.pendingJobService = pendingJobService;
        this.notificationService = notificationService;
    }
 
    public void checkPendingJobsAndNotifyIfNecessary() {
        int pendingJobCount = pendingJobService.getPendingJobCount();
        if (pendingJobCount > MAX_PENDING_JOB_COUNT) {
            notificationService.sendNotification();
        }
    }
}
