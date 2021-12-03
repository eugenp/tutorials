package com.baeldung.architecture.hexagonal.domain.message.spi.persistence;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;

import java.util.Optional;
import java.util.Set;

public interface IGetAllMessagePort {
    Optional<Set<Message>> handle();
}
