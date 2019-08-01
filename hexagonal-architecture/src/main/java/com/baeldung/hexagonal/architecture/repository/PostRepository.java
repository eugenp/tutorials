package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {
}
