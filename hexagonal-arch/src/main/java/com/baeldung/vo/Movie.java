package com.baeldung.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Domain object, its part of core component
 * @author rchaudhary23
 *
 */
@Entity
public class Movie {
	
	@Id
	private int movieId;
	
	private String movieName;
	
	private String yearOfRelease;
	
	private String movieGenre;
	
	private double movieRating;

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getYearOfRelease() {
		return yearOfRelease;
	}

	public void setYearOfRelease(String yearOfRelease) {
		this.yearOfRelease = yearOfRelease;
	}

	public String getMovieGenre() {
		return movieGenre;
	}

	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}

	public double getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(double movieRating) {
		this.movieRating = movieRating;
	}
	
}
