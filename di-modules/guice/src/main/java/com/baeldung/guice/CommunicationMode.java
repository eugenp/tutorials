
package com.baeldung.guice;

import com.baeldung.guice.constant.CommunicationModel;

public interface CommunicationMode {

    public CommunicationModel getMode();
    
    public boolean sendMessage(String message);

}
