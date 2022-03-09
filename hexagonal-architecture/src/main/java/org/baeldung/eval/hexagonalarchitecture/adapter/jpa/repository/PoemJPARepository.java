package org.baeldung.eval.hexagonalarchitecture.adapter.jpa.repository;

import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.adapter.jpa.entity.PoemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoemJPARepository extends JpaRepository<PoemEntity, UUID> {

    public void deleteAllByTitle(String title);
    public PoemEntity findByAuthor(String authorName);
    public PoemEntity findByTitle(String title);

}
