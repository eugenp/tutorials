package com.baeldung.spring.insertableonly.persistable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistableBookRepository extends JpaRepository<PersistableBook, Long> {

}
