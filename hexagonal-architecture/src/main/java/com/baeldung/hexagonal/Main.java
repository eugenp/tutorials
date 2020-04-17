package com.baeldung.hexagonal;

import java.util.List;

import com.baeldung.hexagonal.clients.ConsoleClient;
import com.baeldung.hexagonal.clients.TestClient;
import com.baeldung.hexagonal.domain.MovieData;
import com.baeldung.hexagonal.domain.RetrieveByRatingUseCase;
import com.baeldung.hexagonal.domain.inputPorts.RetrieveByRatingPort;
import com.baeldung.hexagonal.domain.outputPorts.Persistence;
import com.baeldung.hexagonal.framework.TestStorage;

public class Main {

    public static void main(String[] args) {
        Persistence persistence = new TestStorage();
        RetrieveByRatingPort retrieveByRating = new RetrieveByRatingUseCase(persistence);

        // print movies above the rating 9.
        TestClient testClient = new TestClient(retrieveByRating);
        List<MovieData> list = testClient.executeRequest(9);
        printMovies(list);

        // Request a rating from the user and print the movies rated above it.
        ConsoleClient consoleClient = new ConsoleClient(retrieveByRating);
        list = consoleClient.askTheUser();
        printMovies(list);
    }

    private static void printMovies(List<MovieData> movies) {
        movies.forEach(movie -> System.out.println(movie));
        System.out.println();
    }

}
