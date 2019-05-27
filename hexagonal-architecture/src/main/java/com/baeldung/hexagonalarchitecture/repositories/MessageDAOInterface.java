package com.baeldung.hexagonalarchitecture.repositories;

import com.baeldung.hexagonalarchitecture.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageDAOInterface extends CrudRepository<Message, Long> {
    public Iterable<Message> findMessagesBySource(int source);
}
