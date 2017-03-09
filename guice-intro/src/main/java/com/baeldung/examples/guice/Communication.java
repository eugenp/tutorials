
package com.baeldung.examples.guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.logging.Logger;

/**
 *
 * @author Baeldung
 */
public class Communication {

    @Inject
    private Logger logger;

    @Inject
    private DefaultCommunicator communicator;

    public Communication(Boolean keepRecords) {
        if (keepRecords) {
            System.out.println("message logging enabled");
        }
    }

    public boolean sendMessage(String message) {
        return communicator.sendMessage(message);
    }

    public DefaultCommunicator getCommunicator() {
        return this.communicator;
    }

}
