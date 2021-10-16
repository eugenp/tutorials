package com.example.hexagonal.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {
	@Id
	private int movieId;
	private String title;
	private int releaseYear;
	private double imdbRating;
	private int rottenTomatoesScore;

	protected Movie() {
	}

	public Movie(int id, String title, int releaseYear, double imdbRating, int rottenTomatoesScore) {
		this.movieId = id;
		this.title = title;
		this.releaseYear = releaseYear;
		this.imdbRating = imdbRating;
		this.rottenTomatoesScore = rottenTomatoesScore;
	}

	public int getMovieId() {
		return movieId;
	}

	public String getTitle() {
		return title;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public int getRottenTomatoesScore() {
		return rottenTomatoesScore;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", title=" + title + ", releaseYear=" + releaseYear + ", imdbRating="
				+ imdbRating + ", rottenTomatoesScore=" + rottenTomatoesScore + "]";
	}

	

}
