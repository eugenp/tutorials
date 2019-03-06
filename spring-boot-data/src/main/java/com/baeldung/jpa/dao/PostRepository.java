package com.baeldung.jpa.dao;

import com.baeldung.jpa.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface PostRepository extends CrudRepository<Post, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.title = ?2, p.body = ?3 WHERE p.id = ?1")
    void updateTitleAndBodyById(int id, String title, String body);
}
