package com.baeldung.architecture.hexagonal.domain.message.api.persistence;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;

import java.util.Optional;

public interface ISaveMessageService {
    Optional<Message> handle(Message message);
}
