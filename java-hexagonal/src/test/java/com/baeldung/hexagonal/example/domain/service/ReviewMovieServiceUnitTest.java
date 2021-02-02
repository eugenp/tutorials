package com.baeldung.hexagonal.example.domain.service;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.domain.entity.Review;
import com.baeldung.hexagonal.example.exception.MovieNotFoundException;
import com.baeldung.hexagonal.example.exception.ReviewAlreadyExistsException;
import com.baeldung.hexagonal.example.test.helper.MockDatabaseAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static com.baeldung.hexagonal.example.test.helper.TestUtility.randomString;
import static com.baeldung.hexagonal.example.test.helper.TestUtility.testMovie;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewMovieServiceUnitTest {

    ReviewMovieService objectUnderTest;

    MockDatabaseAdapter mockDB;

    @BeforeEach
    void setUp() {
        mockDB = new MockDatabaseAdapter();
        objectUnderTest = new ReviewMovieService(mockDB);
    }

    @Test
    @DisplayName("Given no movies are in the DB, when getting a movie by 'title', then MovieNotFoundException should be thrown.")
    void givenNoMoviesInDB_whenGettingMovieByTitle_thenMovieNotFoundExceptionThrown() {
        mockDB.setMockEntry(null);

        assertThatThrownBy(() -> objectUnderTest.viewMoviesByTitle(UUID.randomUUID().toString()))
          .isInstanceOf(MovieNotFoundException.class);
    }

    @Test
    @DisplayName("Given movie is in the DB, when getting a movie by 'title', then a movie is returned matching the title.")
    void givenMovieIsInDB_whenGettingMovieByTitle_thenMatchingMovieReturned() throws MovieNotFoundException {
        String title = randomString(10);
        Movie movie = testMovie(title);

        mockDB.setMockEntry(movie);

        assertThat(objectUnderTest.viewMoviesByTitle(title))
          .extracting("title")
          .isEqualTo(title);
    }

    @Test
    @DisplayName("Given no movies are in the DB, when getting a movie by 'director', then MovieNotFoundException should be thrown.")
    void givenNoMoviesInDB_whenGettingMovieByDirector_thenMovieNotFoundExceptionThrown() {
        mockDB.setMockEntry(null);

        assertThatThrownBy(() -> objectUnderTest.viewMoviesByDirector(UUID.randomUUID().toString()))
          .isInstanceOf(MovieNotFoundException.class);
    }

    @Test
    @DisplayName("Given movie is in the DB, when getting a movie by 'director', then a movie is returned matching the director.")
    void givenMovieByDirector_whenGettingMovieByDirector_thenMatchingMovieReturned() throws MovieNotFoundException {
        String director = randomString(10);
        Movie movie = testMovie(director);

        mockDB.setMockEntry(movie);

        assertThat(objectUnderTest.viewMoviesByDirector(director))
          .extracting("director")
          .isEqualTo(singletonList(director));
    }

    @Test
    @DisplayName("Given no movies are in the DB, when getting a movie by 'actor', then MovieNotFoundException should be thrown.")
    void givenNoMovieWithActor_whenGettingMovie_thenMovieNotFoundExceptionThrown() {
        mockDB.setMockEntry(null);

        assertThatThrownBy(() -> objectUnderTest.viewMoviesByActor(UUID.randomUUID().toString()))
          .isInstanceOf(MovieNotFoundException.class);
    }

    @Test
    @DisplayName("Given movie is in the DB, when getting a movie by 'actor', then a movie is returned matching the actor.")
    void givenMovieWithActor_whenGettingMovie_thenMovieReturned() throws MovieNotFoundException {
        String actor = randomString(10);
        Movie movie = testMovie(actor);

        mockDB.setMockEntry(movie);

        assertThat(objectUnderTest.viewMoviesByActor(actor))
          .extracting("actors")
          .isEqualTo(singletonList(singletonList(actor)));
    }

    @Test
    @DisplayName("When adding a review that already exists by a specific author, then the new review is rejected.")
    void whenAddingAReviewThatAlreadyExistsFromAuthor_ThenNewReviewIsRejected() {
        String title = randomString(10);
        Movie movie = testMovie(title);
        Review review = new Review();
        review.setAuthor(randomString(10));
        movie.setReviews(singleton(review));

        mockDB.setMockEntry(movie);

        assertThatThrownBy(() -> objectUnderTest.addReview(review, title))
          .isInstanceOf(ReviewAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When adding a review to a movie as a new author, then the review is successfully added to the movie.")
    void whenAddingANewReview_ThenReviewIsAddedToMovie() throws MovieNotFoundException, ReviewAlreadyExistsException {
        String title = randomString(10);
        Movie movie = testMovie(randomString(10));

        Review review = new Review();
        String author = randomString(10);
        review.setAuthor(author);

        mockDB.setMockEntry(movie);

        objectUnderTest.addReview(review, title);

        assertThat(objectUnderTest.viewMoviesByTitle(title))
          .extracting("reviews")
          .extracting(reviews -> ((Set) reviews).iterator().next())
          .extracting("author")
          .isEqualTo(author);
    }


}