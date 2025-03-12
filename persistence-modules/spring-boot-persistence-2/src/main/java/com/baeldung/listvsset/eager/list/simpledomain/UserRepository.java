package com.baeldung.listvsset.eager.list.simpledomain;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

@Lazy(value = false)
public interface UserRepository extends JpaRepository<User, Long> {

}
