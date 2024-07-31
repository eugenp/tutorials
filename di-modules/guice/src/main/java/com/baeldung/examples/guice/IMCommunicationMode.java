
package com.baeldung.examples.guice;

import com.baeldung.examples.guice.aop.MessageSentLoggable;
import com.baeldung.examples.guice.constant.CommunicationModel;
import com.google.inject.Inject;
import java.util.logging.Logger;

/**
 *
 * @author baeldung
 */
public class IMCommunicationMode implements CommunicationMode {

    @Inject
    private Logger logger;

    @Override
    public CommunicationModel getMode() {
        return CommunicationModel.IM;
    }

    @Override
    @MessageSentLoggable
    public boolean sendMessage(String message) {
        logger.info("IM Message Sent");
        return true;
    }

}
