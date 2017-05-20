/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.dependency.injection;

import com.baeldung.spring.dependency.injection.beans.Lyrics;
import com.baeldung.spring.dependency.injection.beans.Song;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author mansi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SongAppJavaConfig.class)
public class SongAppTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Test
    public void givenConstructorBasedDICode_whenCreateLyrics_thenInjectLanguage() {
        Lyrics lyrics = (Lyrics) applicationContext.getBean(Lyrics.class);

        assertNotNull(lyrics.getLanguage());
        assertEquals("English", lyrics.getLanguage().getName());
    }

    @Test
    public void givenSetterBasedDICode_whenCreateSong_thenInjectWriter() {
        Song song = (Song) applicationContext.getBean(Song.class);

        assertNotNull(song.getWriter());
        assertEquals("Adele", song.getWriter().getName());
    }

    @Test
    public void givenConstructorBasedDICode_whenCreateSong_thenInjectSinger() {
        Song song = (Song) applicationContext.getBean(Song.class);

        assertNotNull(song.getSinger());
        assertEquals("Adele", song.getSinger().getName());
    }

    @Test
    public void givenSetterBasedDICode_whenCreateSong_thenInjectLyrics() {
        Song song = (Song) applicationContext.getBean(Song.class);

        assertNotNull(song.getLyrics());
        assertEquals("lyrics.skyfall", song.getLyrics().getId());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
