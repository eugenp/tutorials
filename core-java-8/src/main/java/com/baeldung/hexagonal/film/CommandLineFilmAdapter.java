package com.baeldung.hexagonal.film;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class CommandLineFilmAdapter implements FilmPresentationPort {

    private static final String DELIMITER = ";";

    @Override
    public List<Film> readFilms() {
        try (Reader reader = new InputStreamReader(System.in);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return Arrays.stream(bufferedReader.readLine().split(DELIMITER))
                    .map(Film::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void writeFilms(List<Film> films) {
        films.forEach(film -> System.out.println(film.getName()));
    }
}
