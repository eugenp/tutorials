package com.baeldung.hexarch.demo.core;

import java.util.List;

public interface FilmApp {
    Film addFilm(Film film);
    List<Film> getFilmsOrderedByScore();
}
