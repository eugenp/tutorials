package org.baeldung.repository;

import org.baeldung.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    int countByStatus(int status);

    Optional<User> findOneByName(String name);

    Stream<User> findAllByName(String name);

    @Async
    CompletableFuture<User> findOneByStatus(Integer status);

}
