package com.baeldung.evaluation.adapters.output;

import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;

public class RealNotificationsAdapter implements INotificationsSpi {

        @Override public boolean send() {
                System.out.println("Sending notifications from real adapter.");

                return true;
        }
}
