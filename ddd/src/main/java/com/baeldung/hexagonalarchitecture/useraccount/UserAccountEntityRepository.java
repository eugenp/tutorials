package com.baeldung.hexagonalarchitecture.useraccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 17:19
 */
interface UserAccountEntityRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUsername(String username);

    Optional<UserAccountEntity> findByEmail(String email);

}
