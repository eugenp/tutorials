package com.baeldung.hexarch.demo.core;

import java.util.Collection;

public interface FilmRepo {
    Film insertFilm(Film film);
    Film getFilm(Integer id);
    Collection<Film> listFilms();
}
