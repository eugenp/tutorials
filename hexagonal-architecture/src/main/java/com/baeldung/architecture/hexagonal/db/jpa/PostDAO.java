package com.baeldung.architecture.hexagonal.db.jpa;

import com.baeldung.architecture.hexagonal.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDAO extends JpaRepository<PostEntity, Integer> {

}
