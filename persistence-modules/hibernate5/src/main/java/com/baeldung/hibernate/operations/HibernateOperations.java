package com.baeldung.hibernate.operations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.baeldung.hibernate.pojo.Movie;

/**
 * 
 *Class to illustrate the usage of EntityManager API.
 */
public class HibernateOperations {

    private static final EntityManagerFactory emf;

    /**
     * Static block for creating EntityManagerFactory. The Persistence class looks for META-INF/persistence.xml in the classpath.
     */
    static {
        emf = Persistence.createEntityManagerFactory("com.baeldung.movie_catalog");
    }

    /**
     * Static method returning EntityManager.
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Saves the movie entity into the database. Here we are using Application Managed EntityManager, hence should handle transactions by ourselves.
     */
    public void saveMovie() {
        EntityManager em = HibernateOperations.getEntityManager();
        em.getTransaction()
            .begin();
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setMovieName("The Godfather");
        movie.setReleaseYear(1972);
        movie.setLanguage("English");
        em.persist(movie);
        em.getTransaction()
            .commit();
    }

    /**
     * Method to illustrate the querying support in EntityManager when the result is a single object.
     * @return Movie
     */
    public Movie queryForMovieById() {
        EntityManager em = HibernateOperations.getEntityManager();
        Movie movie = (Movie) em.createQuery("SELECT movie from Movie movie where movie.id = ?1")
            .setParameter(1, new Long(1L))
            .getSingleResult();
        return movie;
    }

    /**
     * Method to illustrate the querying support in EntityManager when the result is a list.
     * @return
     */
    public List<?> queryForMovies() {
        EntityManager em = HibernateOperations.getEntityManager();
        List<?> movies = em.createQuery("SELECT movie from Movie movie where movie.language = ?1")
            .setParameter(1, "English")
            .getResultList();
        return movies;
    }

    /**
     * Method to illustrate the usage of find() method.
     * @param movieId
     * @return Movie
     */
    public Movie getMovie(Long movieId) {
        EntityManager em = HibernateOperations.getEntityManager();
        Movie movie = em.find(Movie.class, new Long(movieId));
        return movie;
    }

    /**
     * Method to illustrate the usage of merge() function.
     */
    public void mergeMovie() {
        EntityManager em = HibernateOperations.getEntityManager();
        Movie movie = getMovie(1L);
        em.detach(movie);
        movie.setLanguage("Italian");
        em.getTransaction()
            .begin();
        em.merge(movie);
        em.getTransaction()
            .commit();
    }

    /**
     * Method to illustrate the usage of remove() function.
     */
    public void removeMovie() {
        EntityManager em = HibernateOperations.getEntityManager();
        em.getTransaction()
            .begin();
        Movie movie = em.find(Movie.class, new Long(1L));
        em.remove(movie);
        em.getTransaction()
            .commit();
    }

}
