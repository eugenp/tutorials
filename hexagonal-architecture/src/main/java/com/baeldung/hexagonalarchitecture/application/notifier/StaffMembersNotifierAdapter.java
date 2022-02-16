package com.baeldung.hexagonalarchitecture.application.notifier;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchitecture.domain.StaffMembersNotification;
import com.baeldung.hexagonalarchitecture.domain.StaffMembersNotifier;

@Component
public class StaffMembersNotifierAdapter implements StaffMembersNotifier {

    @Override
    public void sendNotification(StaffMembersNotification notification) {
        System.out.println("sending notification to the team: " + notification.getTeam());
    }
}
