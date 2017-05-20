package com.baeldung.spring.dependency.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.dependency.injection.beans.Lyrics;
import com.baeldung.spring.dependency.injection.beans.Song;

public class SongApp {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SongAppJavaConfig.class);

		Song song = (Song) context.getBean("song");
		song.printSongName();
		song.getLyrics().printLyricsId();
		song.getSinger().printSingerName();
		song.getWriter().printWriterName();

		Lyrics lyrics = (Lyrics) context.getBean("lyrics");
		lyrics.getLanguage().printLanguageName();

	}
}
