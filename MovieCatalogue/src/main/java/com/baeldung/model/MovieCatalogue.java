package com.baeldung.model;

//Domain Entity 
public class MovieCatalogue{
	private String movieName; 
	private int movieIdentifier;
	private String movieDirector; 
	private double movieRating;
	
	
	public MovieCatalogue(String movieName, int movieIdentifier, String movieDirector, double movieRating) {
		super();
		this.movieName = movieName;
		this.movieIdentifier = movieIdentifier;
		this.movieDirector = movieDirector;
		this.movieRating = movieRating;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getMovieIdentifier() {
		return movieIdentifier;
	}
	public void setMovieIdentifier(int movieIdentifier) {
		this.movieIdentifier = movieIdentifier;
	}
	public String getMovieDirector() {
		return movieDirector;
	}
	public void setMovieDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}
	public double getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(double movieRating) {
		this.movieRating = movieRating;
	}

}
