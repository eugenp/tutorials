package com.baeldung.spring.data.persistence.findvsget.repository;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NewTransactionUserRepository extends JpaRepository<User, Long> {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User getReferenceById(Long id);

    @Override
    Optional<User> findById(Long id);
}
