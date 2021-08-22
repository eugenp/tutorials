package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesJpaRepository extends JpaRepository<Note, Long> {

}
