package com.baeldung.evaluation.adapters.output;

import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;

public class NotificationsAdapter implements INotificationsSpi {
    public boolean send() {
        System.out.println("Sending notifications from real adapter.");

        return true;
    }
}
