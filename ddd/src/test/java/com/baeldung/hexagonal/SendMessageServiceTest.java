package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adaptor.AirphoneSendMessageAdaptor;
import com.baeldung.hexagonal.domain.MessageRequest;
import com.baeldung.hexagonal.domain.MessageResponse;
import com.baeldung.hexagonal.port.SendMessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendMessageServiceTest {
    @Test
    void testSendMessageService() {
        SendMessageService sendMessageService = new AirphoneSendMessageAdaptor();
        MessageRequest messageRequest = new MessageRequest("Body", "dest", "source", "messageId");
        MessageResponse messageResponse = sendMessageService.sendMessage(messageRequest);
        assertEquals("Chase bank", messageResponse.isDelivered());
        assertEquals("randomChaseMessageId", messageResponse.getMessageId());
    }
}
