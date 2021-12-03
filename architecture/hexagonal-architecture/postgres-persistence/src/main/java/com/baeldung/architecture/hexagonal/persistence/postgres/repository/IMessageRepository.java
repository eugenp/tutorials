package com.baeldung.architecture.hexagonal.persistence.postgres.repository;

import com.baeldung.architecture.hexagonal.persistence.postgres.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageRepository extends JpaRepository<MessageEntity, Long> {
}
