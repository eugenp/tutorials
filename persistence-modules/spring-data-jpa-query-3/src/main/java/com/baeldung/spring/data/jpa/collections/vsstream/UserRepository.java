package com.baeldung.spring.data.jpa.collections.vsstream;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Stream<User> findAllByAgeGreaterThan(int age);

  List<User> findByAgeGreaterThan(int age);
}
