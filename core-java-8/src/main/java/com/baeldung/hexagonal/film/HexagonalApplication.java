package com.baeldung.hexagonal.film;

import java.io.File;

class HexagonalApplication {
    public static void main(String[] args) {
        FilmPresentationPort fileFilmAdapter = new FileFilmAdapter(new File("test.txt"));
        FilmPresentationPort commandLineFilmAdapter = new CommandLineFilmAdapter();
        commandLineFilmAdapter.writeFilms(fileFilmAdapter.readFilms());
    }
}
