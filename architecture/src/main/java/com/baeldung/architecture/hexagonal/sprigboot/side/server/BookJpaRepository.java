package com.baeldung.architecture.hexagonal.sprigboot.side.server;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByNameContaining(String partOfTheName);

}
