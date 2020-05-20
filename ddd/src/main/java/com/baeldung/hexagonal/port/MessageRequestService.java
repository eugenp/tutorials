package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.MessageRequest;

public interface MessageRequestService {
    MessageRequest receiveRequest();
}
