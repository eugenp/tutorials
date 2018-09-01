package org.baeldung.repository;

import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    Stream<User> findAllByName(String name);

}
