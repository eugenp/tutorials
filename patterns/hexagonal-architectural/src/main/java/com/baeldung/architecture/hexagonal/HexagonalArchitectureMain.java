package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.*;

import java.io.IOException;

public class HexagonalArchitectureMain {
    public static void main(String[] args) throws IOException {

        // 1.1 This is the Adapter from Infrastructure layer to Domain layer
        MovieDataSourceAdapter movieDataSourceAdapter = new MovieFileDataSourceAdapter();

        // 1.2 This is the Adapter from Infrastructure layer to Domain layer
        MovieDisplayAdapter movieDisplayAdapter = new ConsoleMovieDisplayAdapter();

        // 2. This is the Domain layer (Business Logic) which needs Port from Infrastructure
        MovieSearch movieSearch = new MovieSearch(movieDataSourceAdapter, movieDisplayAdapter);

        // 3. This is the Adapter from Application layer
        MovieLookupAdapter movieLookupAdapter = new ConsoleMovieLookupAdapter(movieSearch);

        // 4. Start
        movieLookupAdapter.perform();
    }
}
