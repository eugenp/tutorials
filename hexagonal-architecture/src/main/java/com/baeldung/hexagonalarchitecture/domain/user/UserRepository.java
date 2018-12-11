package com.baeldung.hexagonalarchitecture.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class has function of "adapter" to database
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
