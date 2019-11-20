package com.baeldung.evaluation.adapters.output;

import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;

public class MockNotificationsAdapter implements INotificationsSpi {
    public boolean send() {
        System.out.println("Sending notifications from mocked adapter.");
        return true;
    }
}
