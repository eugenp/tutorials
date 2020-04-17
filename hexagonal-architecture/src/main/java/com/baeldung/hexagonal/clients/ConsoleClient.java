package com.baeldung.hexagonal.clients;

import java.util.List;
import java.util.Scanner;

import com.baeldung.hexagonal.domain.MovieData;
import com.baeldung.hexagonal.domain.inputPorts.RetrieveByRatingPort;

public class ConsoleClient {

    private RetrieveByRatingPort retrieveByRatingPort;

    private static Scanner in = new Scanner(System.in);

    public ConsoleClient(RetrieveByRatingPort retrieveByratingPort) {
        this.retrieveByRatingPort = retrieveByratingPort;

    }

    public List<MovieData> askTheUser() {
        System.out.print("Please enter a rating, we will list the movies rated above it: ");
        float rating = in.nextFloat();

        return retrieveByRatingPort.retrieveMoviesAbove(rating);
    }

}
