package com.baeldung.hexagonal;


import com.baeldung.hexagonal.adaptor.ChasingBankMessageRequestAdaptor;
import com.baeldung.hexagonal.adaptor.ShoppingStopMessageRequestAdaptor;
import com.baeldung.hexagonal.domain.MessageRequest;
import com.baeldung.hexagonal.port.MessageRequestService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageRequestServiceTest {
    @Test
    void testMessageRequestService() {
        MessageRequestService sendMessageService = new ChasingBankMessageRequestAdaptor();
        MessageRequest messageRequest = sendMessageService.receiveRequest();
        assertEquals("Chasing Bank", messageRequest.getSource());
        assertEquals("randomChasemessageId", messageRequest.getMessageId());

        sendMessageService = new ShoppingStopMessageRequestAdaptor();
        messageRequest = sendMessageService.receiveRequest();
        assertEquals("Shopping stop", messageRequest.getSource());
        assertEquals("randomSSMessageId", messageRequest.getMessageId());
    }
}
