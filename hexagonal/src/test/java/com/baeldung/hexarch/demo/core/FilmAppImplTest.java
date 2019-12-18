package com.baeldung.hexarch.demo.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilmAppImplTest {

    private FilmRepo repo;

    private FilmAppImpl filmApp;

    @BeforeEach
    void beforeEach() {
        repo = mock(FilmRepo.class);

        filmApp = new FilmAppImpl(repo);
    }

    @Test
    void addFilm() {
        Film predator = new Film();
        predator.setId(1);
        predator.setName("Predator");
        predator.setScore(99);

        when(repo.getFilm(1)).thenReturn(null);
        when(repo.insertFilm(predator)).thenReturn(predator);

        Film result = filmApp.addFilm(predator);

        assertThat(result).isEqualTo(predator);

        verify(repo).getFilm(1);
        verify(repo).insertFilm(predator);
    }

    @Test
    void getFilmsOrderedByScore() {
        Film predator = new Film();
        predator.setId(1);
        predator.setName("Predator");
        predator.setScore(99);

        Film homeAlone = new Film();
        homeAlone.setId(2);
        homeAlone.setName("Home Alone");
        homeAlone.setScore(1);

        when(repo.listFilms()).thenReturn(List.of(
                homeAlone, predator
        ));

        List<Film> result = filmApp.getFilmsOrderedByScore();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(predator);
        assertThat(result.get(1)).isEqualTo(homeAlone);

        verify(repo).listFilms();
    }
}
