package com.baeldung.hexagonal.application.service;

import com.baeldung.hexagonal.application.port.input.SuggestMovies;
import com.baeldung.hexagonal.application.port.output.FindMovies;
import com.baeldung.hexagonal.application.port.output.FindUser;
import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.User;

import java.util.Set;

public class MovieService implements SuggestMovies {
    private FindUser findUser;
    private FindMovies searchMovies;

    public MovieService(FindUser findUser, FindMovies findMovies) {
        this.findUser = findUser;
        this.searchMovies = findMovies;
    }

    public Set<Movie> toUserByName(String name) {
        User user = findUser.byName(name);
        return searchMovies.byGenre(user.getPreferredGenre());
    }
}
