package driverports;

import core.MovieApp;
import model.MovieSearchRequest;

import static com.google.common.base.Preconditions.checkState;

public class UserInputHandler implements IUserInput {

    private static final String MOVIE_NAME = "movieName";
    private MovieApp movieApp;

    public UserInputHandler(MovieApp movieApp) {
        this.movieApp = movieApp;
    }

    public void handleUserInput(String[] args) {
        validateInput(args);
        MovieSearchRequest movieSearchRequest = new MovieSearchRequest(args[1]);

        movieApp.displayMovieReviews(movieSearchRequest);
    }

    private void validateInput(String[] args) {
        checkState(args.length > 1, "Invalid input string size");
        checkState(args[0].equals(MOVIE_NAME), "Invalid command name");
    }
}
