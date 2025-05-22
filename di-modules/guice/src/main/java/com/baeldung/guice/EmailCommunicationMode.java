
package com.baeldung.guice;

import com.baeldung.guice.aop.MessageSentLoggable;
import com.baeldung.guice.constant.CommunicationModel;

/**
 *
 * @author baeldung
 */
public class EmailCommunicationMode implements CommunicationMode {

    @Override
    public CommunicationModel getMode() {
        return CommunicationModel.EMAIL;
    }

    @Override
    @MessageSentLoggable
    public boolean sendMessage(String Message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
