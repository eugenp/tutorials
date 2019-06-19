package com.baeldung.hexagonal.persistence.repository;

import com.baeldung.hexagonal.persistence.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PostJpaRepository extends CrudRepository<Post, String> {

}
