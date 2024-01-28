package com.baeldung.nplusone.defaultfetch.list;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

@Lazy(value = false)
public interface UserRepository extends JpaRepository<User, Long> {

}
