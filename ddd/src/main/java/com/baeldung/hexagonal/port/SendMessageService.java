package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.MessageRequest;
import com.baeldung.hexagonal.domain.MessageResponse;

public interface SendMessageService {
    MessageResponse sendMessage(MessageRequest messageRequest);
}
