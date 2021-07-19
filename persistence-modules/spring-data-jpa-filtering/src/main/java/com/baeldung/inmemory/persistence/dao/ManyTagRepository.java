package com.baeldung.inmemory.persistence.dao;

import com.baeldung.inmemory.persistence.model.ManyTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManyTagRepository extends JpaRepository<ManyTag, Long> {
}
