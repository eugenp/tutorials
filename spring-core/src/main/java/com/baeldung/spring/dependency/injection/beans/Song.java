package com.baeldung.spring.dependency.injection.beans;

import org.springframework.beans.factory.annotation.Autowired;

public class Song {

	private String name;
	private Lyrics lyrics;
	private Singer singer;
	private Writer writer;

	public Song() {
	}

	// constructor-based dependency injection of Singer
	// using xml configuration
	public Song(Singer singer) {
		this.singer = singer;
	}

	// setter-based dependency injection of Lyrics
	// using xml configuration
	public void setLyrics(Lyrics lyrics) {
		this.lyrics = lyrics;
	}

	public Lyrics getLyrics() {
		return lyrics;
	}

	// setter-based dependency injection of Lyrics
	// using annotation
	@Autowired
	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public Writer getWriter() {
		return writer;
	}

	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void printSongName() {
		System.out.println("Song Name : "+name);
	}
}
