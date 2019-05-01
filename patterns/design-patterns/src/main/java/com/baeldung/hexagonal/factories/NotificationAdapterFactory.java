package com.baeldung.hexagonal.factories;

import com.baeldung.hexagonal.adapters.SMSNotificationAdapter;
import com.baeldung.hexagonal.domain.NotificationHandle;
import com.baeldung.hexagonal.ports.NotificationSenderPort;


public class NotificationAdapterFactory {

    public NotificationSenderPort getPort(NotificationHandle.Type type) {
        switch (type) {
            case SMS:
                return new SMSNotificationAdapter();
            //handle other cases
            default:
                return null;
        }
    }

}
