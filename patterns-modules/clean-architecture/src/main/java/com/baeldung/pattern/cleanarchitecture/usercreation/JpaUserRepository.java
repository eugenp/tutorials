package com.baeldung.pattern.cleanarchitecture.usercreation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaUserRepository extends JpaRepository<UserDataMapper, String> {
}
