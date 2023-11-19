
package com.baeldung.examples.guice;

import com.baeldung.examples.guice.marker.Communicator;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 *
 * @author baeldung
 */
public class DefaultCommunicator implements Communicator {

    private CommunicationMode defaultCommsMode;
    @Inject
    @Named("SMSComms")
    CommunicationMode smsCommsMode;
    @Inject
    @Named("EmailComms")
    CommunicationMode emailCommsMode;
    @Inject
    @Named("IMComms")
    CommunicationMode imCommsMode;

    protected DefaultCommunicator(CommunicationMode defaultComms) {
        this.defaultCommsMode = defaultComms;
    }

    public DefaultCommunicator() {

    }

    public boolean sendMessage(String message) {
        boolean sent = false;
        if (defaultCommsMode != null) {
            sent = sendMessageByDefault(message);
        } else {
            sent = smsCommsMode.sendMessage(message);
        }
        return sent;
    }

    private boolean sendMessageByDefault(String message) {
        boolean sent = false;
        if (message != null && !message.trim().equals("")) {
            return defaultCommsMode.sendMessage(message);
        }
        return sent;
    }

}
