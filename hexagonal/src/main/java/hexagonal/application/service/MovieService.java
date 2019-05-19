package hexagonal.application.service;

import hexagonal.application.port.input.SuggestMovies;
import hexagonal.application.port.output.FindMovies;
import hexagonal.application.port.output.FindUser;
import hexagonal.domain.Movie;
import hexagonal.domain.User;

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
