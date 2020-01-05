package model;

import org.requirementsascode.Model;

import java.util.function.Consumer;

public class CommandMapperModel {
    private static final Class<MovieSearchRequest> searchMovies = MovieSearchRequest.class;

    public static Model build(Consumer<MovieSearchRequest> displayMovies) {
        Model model = Model.builder()
                .user(searchMovies).system(displayMovies)
                .build();

        return model;
    }
}