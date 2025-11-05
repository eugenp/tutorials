package com.baeldung.springsecurity.repository;

import com.baeldung.springsecurity.entity.Post;
import com.baeldung.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
