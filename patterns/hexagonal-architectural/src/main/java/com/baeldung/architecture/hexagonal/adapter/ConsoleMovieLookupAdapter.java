package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.MovieSearch;
import com.baeldung.architecture.hexagonal.port.MovieLookup;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleMovieLookupAdapter implements MovieLookup {
    private MovieSearch movieSearch;
    public ConsoleMovieLookupAdapter(MovieSearch movieSearch) {
        this.movieSearch = movieSearch;
    }

    public void perform() throws IOException {
        System.out.print("Enter a movie title: ");

        Scanner sc = new Scanner(System.in);
        String movieTitle = sc.next();
        this.movieSearch.search(movieTitle);
    }
}
