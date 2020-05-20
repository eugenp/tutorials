package com.baeldung.hexagonal.adaptor;

import com.baeldung.hexagonal.domain.MessageRequest;
import com.baeldung.hexagonal.domain.MessageResponse;
import com.baeldung.hexagonal.port.SendMessageService;

public class AirphoneSendMessageAdaptor implements SendMessageService {
    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        System.out.println("Sent messageId=" + messageRequest.messageId + ",  " + "body=" +
                messageRequest.body + ", to dest=" + messageRequest.dest + ", from source=" +
                messageRequest.source + ", over Airphone network");
        return new MessageResponse(true, messageRequest.messageId);
    }
}
