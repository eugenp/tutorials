
package com.baeldung.examples.guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 *
 * @author Baeldung
 */
public class Communication {

    final Date start = new Date();

    @Inject
    private Logger logger;

    private Queue<String> messageLog;

    @Named("CommsUUID")
    private String commsID;

    @Inject
    private DefaultCommunicator communicator;

    public Communication(Boolean keepRecords) {
        if (keepRecords) {
            messageLog = new LinkedList();
        }
    }

    public boolean sendMessage(String message) {
        if (!message.isEmpty() && messageLog != null) {
            messageLog.add(message);
        }
        return communicator.sendMessage(message);
    }

    public void print() {
        if (messageLog != null) {
            for (String message : messageLog) {
                logger.info(message);
            }
        } else {
            logger.info("Message logging wasn't enabled");
        }
    }

    public DefaultCommunicator getCommunicator() {
        return this.communicator;
    }

}
