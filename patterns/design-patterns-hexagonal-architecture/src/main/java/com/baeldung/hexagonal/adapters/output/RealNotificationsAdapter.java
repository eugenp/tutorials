package com.baeldung.hexagonal.adapters.output;

import com.baeldung.hexagonal.spi.INotificationsSpi;

public class RealNotificationsAdapter implements INotificationsSpi {

        @Override public boolean send() {
                System.out.println("Sending notifications from real adapter.");

                return true;
        }
}
