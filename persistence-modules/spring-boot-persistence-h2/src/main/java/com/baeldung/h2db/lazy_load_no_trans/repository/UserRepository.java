package com.baeldung.h2db.lazy_load_no_trans.repository;

import com.baeldung.h2db.lazy_load_no_trans.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}