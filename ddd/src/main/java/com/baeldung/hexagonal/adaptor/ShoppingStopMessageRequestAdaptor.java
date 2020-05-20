package com.baeldung.hexagonal.adaptor;

import com.baeldung.hexagonal.domain.MessageRequest;
import com.baeldung.hexagonal.port.MessageRequestService;

public class ShoppingStopMessageRequestAdaptor implements MessageRequestService {
    @Override
    public MessageRequest receiveRequest() {
        return new MessageRequest("Thank you for shopping with us", "1234567890", "Shopping stop",
                "randomSSMessageId");
    }
}
