package com.baeldung.hexagonal.architecture.port;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.hexagonal.architecture.model.Movie;
import com.baeldung.hexagonal.architecture.service.MovieServiceImplementation;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class MovieServiceUnitTest {
    
    @TestConfiguration
    static class MovieServiceTestConfig {
        @Bean
        public MovieService movieService() {
            return new MovieServiceImplementation();
        }
    }
    @MockBean
    MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;
    
    @BeforeEach
    public void setUp() {
        Movie movie = new Movie(1, "The Predator",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "From the outer reaches of space to the small-town streets of suburbia,"
            + " the hunt comes home in Shane Blacks explosive reinvention of the Predator"
            + " series. Now, the universes most lethal hunters are stronger,"
            + " smarter and deadlier than ever before,"
            + " having genetically upgraded themselves with DNA from other species."
            + " When a young boy accidentally triggers their return to Earth,"
            + " only a ragtag crew of ex-soldiers and a disgruntled science teacher"
            + " can prevent the end of the human race.", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 180);

        Movie movie1 = new Movie(2, "Avengers",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "The Avengers Movie", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);

        Movie movie2 = new Movie(3, "Star Wars",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "Star Wars", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);
        
        List<Movie> movies = Arrays.asList(movie, movie1);

        Mockito.when(movieRepository.getMovies()).thenReturn(movies);
        Mockito.when(movieRepository.getMovieById(movie.getMovieId())).thenReturn(movie);
        Mockito.when(movieRepository.getMovieById(4)).thenReturn(null);
        Mockito.when(movieRepository.addMovie(movie2)).thenReturn(movie2);
        Mockito.when(movieRepository.removeMovie(movie2.getMovieId())).thenReturn(movie2);
    }
    
    @Test
    public void whenValidMovieId_thenMovieShouldBeFound() {
        Integer id = 1;
        Movie movie = movieService.getMovieById(1);

        assertThat(movie.getMovieId()).isEqualTo(id);
        verifyGetByMovieIdIsCalledOnce();
    }

    @Test
    public void whenInValidProductId_thenProductShouldNotBeFound() {
        Movie movie = movieService.getMovieById(4);

        assertThat(movie).isNull();
        verifyGetByMovieIdIsCalledOnce();
    }

    @Test
    public void givenTwoMovies_whenGetAllMovies_thenTwoMoviesReturned() {
        Movie movie = new Movie(1, "The Predator",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "From the outer reaches of space to the small-town streets of suburbia,"
            + " the hunt comes home in Shane Blacks explosive reinvention of the Predator"
            + " series. Now, the universes most lethal hunters are stronger,"
            + " smarter and deadlier than ever before,"
            + " having genetically upgraded themselves with DNA from other species."
            + " When a young boy accidentally triggers their return to Earth,"
            + " only a ragtag crew of ex-soldiers and a disgruntled science teacher"
            + " can prevent the end of the human race.", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 180);

        Movie movie1 = new Movie(2, "Avengers",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "The Avengers Movie", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);


        List<Movie> allMovies = movieService.getMovies();

        assertThat(allMovies).hasSize(2).extracting(Movie::getMovieName).contains(movie.getMovieName(), movie1.getMovieName());
        verifyGetMoviesIsCalledOnce();;
    }

    @Test
    public void whenAddMovie_thenMovieNameIsMatched() {
        Movie movie2 = new Movie(3, "Star Wars",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "Star Wars", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);
        
        Movie result = movieService.addMovie(movie2);
       
        assertThat(result).extracting(Movie::getMovieName).as(movie2.getMovieName());
        
    }

    @Test
    public void whenRemoveMovieById_thenOneMovieReturned() {
        Movie movie = new Movie(3, "Star Wars",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "Star Wars", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);


        assertThat(movieService.removeMovie(3)).extracting(Movie::getMovieName).as(movie.getMovieName());
    }

    private void verifyGetByMovieIdIsCalledOnce() {
        Mockito.verify(movieRepository, VerificationModeFactory.times(1)).getMovieById(Mockito.anyInt());
        Mockito.reset(movieRepository);
    }

    private void verifyGetMoviesIsCalledOnce() {
        Mockito.verify(movieRepository, VerificationModeFactory.times(1)).getMovies();
        Mockito.reset(movieRepository);
    }
   
}
