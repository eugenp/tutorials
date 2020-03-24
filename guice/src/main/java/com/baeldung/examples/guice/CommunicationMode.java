
package com.baeldung.examples.guice;

import com.baeldung.examples.guice.constant.CommunicationModel;

public interface CommunicationMode {

    public CommunicationModel getMode();
    
    public boolean sendMessage(String message);

}
