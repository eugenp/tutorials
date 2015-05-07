package org.baeldung.persistence.dao;

import java.util.Date;
import java.util.List;

import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findBySubmissionDateBeforeAndIsSent(Date date, boolean isSent);

    List<Post> findByUser(User user);

    List<Post> findByRedditIDNotNullAndNoOfAttemptsGreaterThan(int attempts);
}