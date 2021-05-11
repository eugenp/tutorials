package com.baeldung.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.dao.MovieDao;
import com.baeldung.model.MovieCatalogue;
import com.baeldung.service.MovieCatalogueService;

@ExtendWith(MockitoExtension.class)
public class MovieCatalogueControllerTest {

	@InjectMocks
	MovieCatalogueController movieCatalogueController;

	@Mock
	MovieCatalogueService movieCatalogueService;

	@Mock
	MovieDao movieDao;

	@Test
	public void FindAllMoviesTEST() {
		
		// given
		
		MovieCatalogue movieCatalogue1 = new MovieCatalogue("Games Of Thrones", 1, "Alan Taylor", 9.3);
		MovieCatalogue movieCatalogue2 = new MovieCatalogue("The Avnegers", 2, "Anthony Russo", 8);

		List<MovieCatalogue> list = new ArrayList<MovieCatalogue>();
		list.addAll(Arrays.asList(movieCatalogue1, movieCatalogue2));

		when(movieCatalogueService.getAllMovies()).thenReturn(list);

		// when
		List<MovieCatalogue> result = movieCatalogueController.getAllMovies();

		// then
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.contains(movieCatalogue1));
		assertThat(result.get(0).getMovieName()).isEqualTo(movieCatalogue1.getMovieName());
		assertThat(result.get(1).getMovieDirector()).isEqualTo(movieCatalogue2.getMovieDirector());

	}
}
