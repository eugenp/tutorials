package com.baeldung.architecture.hexagonal;

public class MovieService {
    private MovieDaoInterface dao;

    public MovieService(MovieDaoInterface movieDao) {
        dao = movieDao;
    }

    public Movie search(String name) {
        return dao.get(name);
    }
}