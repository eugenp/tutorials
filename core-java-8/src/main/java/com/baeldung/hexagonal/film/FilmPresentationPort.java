package com.baeldung.hexagonal.film;

import java.util.List;

interface FilmPresentationPort {
    List<Film> readFilms();

    void writeFilms(List<Film> films);
}
