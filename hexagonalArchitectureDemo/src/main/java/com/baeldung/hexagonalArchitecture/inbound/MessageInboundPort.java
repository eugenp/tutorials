package com.baeldung.hexagonalArchitecture.inbound;

import com.baeldung.hexagonalArchitecture.entity.Message;

public interface MessageInboundPort {
	
    public void addMessage(Message  msg);	

}
