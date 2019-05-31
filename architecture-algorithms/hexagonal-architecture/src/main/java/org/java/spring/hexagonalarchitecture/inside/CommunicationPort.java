package org.java.spring.hexagonalarchitecture.inside;

public interface CommunicationPort {

    public boolean sendMessage(String channelType, String message, Person receiver);

}
