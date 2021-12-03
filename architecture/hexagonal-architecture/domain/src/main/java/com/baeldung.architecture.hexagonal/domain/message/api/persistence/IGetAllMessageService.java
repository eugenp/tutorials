package com.baeldung.architecture.hexagonal.domain.message.api.persistence;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;

import java.util.Optional;
import java.util.Set;

public interface IGetAllMessageService {
    Optional<Set<Message>> handle();
}
