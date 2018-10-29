package org.baeldung.inmemory.persistence.dao;

import org.baeldung.inmemory.persistence.model.ManyTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManyTagRepository extends JpaRepository<ManyTag, Long> {
}
