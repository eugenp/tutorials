package com.baeldung.hexagonal.movie.domain;

import java.util.Optional;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Movie {

    @Getter private final MovieId movieId;

    @Getter private final Title title;

    @Getter private final Price rentPrice;

    @Getter private final Genre genre;

    public static Movie withoutId(Title title, Price rentPrice, Genre genre){
        return new Movie(new MovieId(UUID.randomUUID()), title, rentPrice, genre);
    }

    public static Movie withId(MovieId movieId,Title title, Price rentPrice, Genre genre){
        return new Movie(movieId, title, rentPrice, genre);
    }

    public Optional<MovieId> getId(){
        return Optional.ofNullable(this.movieId);
    }

    @Value
	public static class MovieId {
		private UUID value;
	}

    @Value
	public static class Title {
		private String title;
	}
}
