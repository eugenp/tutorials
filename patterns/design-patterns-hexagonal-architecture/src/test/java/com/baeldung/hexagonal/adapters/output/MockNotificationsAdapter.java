package com.baeldung.hexagonal.adapters.output;

import com.baeldung.hexagonal.spi.INotificationsSpi;
import com.baeldung.hexagonal.spi.IPersistenceSpi;

public class MockNotificationsAdapter implements INotificationsSpi {

        @Override public boolean send() {
                System.out.println("Sending notifications from mocked adapter.");
                return true;
        }
}
