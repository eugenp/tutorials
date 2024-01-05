package com.baeldung.spring.insertableonly.unpadatable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnapdatableBookRepository extends JpaRepository<UnapdatableBook, Long> {

}
