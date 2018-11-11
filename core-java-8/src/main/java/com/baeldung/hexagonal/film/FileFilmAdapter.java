package com.baeldung.hexagonal.film;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

class FileFilmAdapter implements FilmPresentationPort {
    private final File file;

    FileFilmAdapter(File file) {
        this.file = file;
    }

    @Override
    public List<Film> readFilms() {
        try (Reader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.lines()
                    .map(Film::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void writeFilms(List<Film> films) {
        try (Writer writer = new FileWriter(file)) {
            writer.write(films.stream()
                    .map(Film::getName)
                    .collect(joining("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
