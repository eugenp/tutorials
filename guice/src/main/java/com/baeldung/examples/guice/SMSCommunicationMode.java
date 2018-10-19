
package com.baeldung.examples.guice;

import com.baeldung.examples.guice.aop.MessageSentLoggable;
import com.baeldung.examples.guice.constant.CommunicationModel;
import com.google.inject.Inject;
import java.util.logging.Logger;

/**
 *
 * @author baeldung
 */
public class SMSCommunicationMode implements CommunicationMode {
    
    @Inject
    private Logger logger;

    @Override
    public CommunicationModel getMode() {
        return CommunicationModel.SMS;
    }

    @Override
    @MessageSentLoggable
    public boolean sendMessage(String message) {
        logger.info("SMS message sent");
        return true;
    }

}
