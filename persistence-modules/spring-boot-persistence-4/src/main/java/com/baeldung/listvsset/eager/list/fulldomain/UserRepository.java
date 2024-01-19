package com.baeldung.listvsset.eager.list.fulldomain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
