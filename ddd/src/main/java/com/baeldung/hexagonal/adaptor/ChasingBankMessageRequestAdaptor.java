package com.baeldung.hexagonal.adaptor;

import com.baeldung.hexagonal.domain.MessageRequest;
import com.baeldung.hexagonal.port.MessageRequestService;

public class ChasingBankMessageRequestAdaptor implements MessageRequestService {
    @Override
    public MessageRequest receiveRequest() {
        return new MessageRequest("Money withdrawn from account", "1234567890", "Chasing Bank",
                "randomChaseMessageId");
    }
}
