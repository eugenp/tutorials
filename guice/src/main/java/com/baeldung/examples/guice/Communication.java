
package com.baeldung.examples.guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 *
 * @author baeldung
 */
public class Communication {

    final Date start = new Date();

    @Inject
    private Logger logger;

    @Inject
    private DefaultCommunicator communicator;

    public Communication(Boolean keepRecords) {
        if (keepRecords) {
            System.out.println("keeping records");
        }
    }

    public boolean sendMessage(String message) {
      
        return communicator.sendMessage(message);
    }
 
    public DefaultCommunicator getCommunicator() {
        return this.communicator;
    }

}
