package com.baeldung.hibernate.operations;

import com.baeldung.hibernate.pojo.Movie;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MovieService {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveMovie() {
        entityManager.getTransaction()
          .begin();

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setMovieName("The Godfather");
        movie.setReleaseYear(1972);
        movie.setLanguage("English");

        entityManager.persist(movie);
        entityManager.getTransaction()
          .commit();
    }

    public Movie getMovie(Long movieId) {
        return entityManager.find(Movie.class, movieId);
    }
}
