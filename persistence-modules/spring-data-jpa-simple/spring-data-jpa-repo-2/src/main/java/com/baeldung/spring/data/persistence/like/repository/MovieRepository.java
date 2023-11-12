package com.baeldung.spring.data.persistence.like.repository;

import com.baeldung.spring.data.persistence.like.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitleContaining(String title);

    List<Movie> findByTitleLike(String title);

    List<Movie> findByTitleContains(String title);

    List<Movie> findByTitleIsContaining(String title);

    List<Movie> findByRatingStartsWith(String rating);

    List<Movie> findByDirectorEndsWith(String director);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByRatingNotContaining(String rating);

    List<Movie> findByDirectorNotLike(String director);

    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:title%")
    List<Movie> searchByTitleLike(@Param("title") String title);

    @Query("SELECT m FROM Movie m WHERE m.rating LIKE ?1%")
    List<Movie> searchByRatingStartsWith(String rating);

    //Escaping works in SpringBoot >= 2.4.1
    //@Query("SELECT m FROM Movie m WHERE m.director LIKE %?#{escape([0])} escape ?#{escapeCharacter()}")
    @Query("SELECT m FROM Movie m WHERE m.director LIKE %:#{[0]}")
    List<Movie> searchByDirectorEndsWith(String director);
}
