package org.java.spring.hexagonalarchitecture.outside;

import org.java.spring.hexagonalarchitecture.inside.CommunicationPort;
import org.java.spring.hexagonalarchitecture.inside.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("EmailCommunicationAdapter")
public class EmailCommunicationAdapter implements CommunicationPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRestControllerAdapter.class);

    @Override
    public boolean sendMessage(String channelType, String message, Person receiver) {
        LOGGER.info("channelType=" + channelType + " message=\"" + message + "\" receiver=" + receiver.getEmail());
        // Implement Logic to send Email
        return false;
    }

}
