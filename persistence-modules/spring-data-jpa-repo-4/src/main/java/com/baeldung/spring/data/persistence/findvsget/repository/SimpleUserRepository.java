package com.baeldung.spring.data.persistence.findvsget.repository;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleUserRepository extends JpaRepository<User, Long> {

}
