package com.baeldung.object.copy;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SharedPlaylistUnitTest {

    Playlist originalPlaylist;

    @BeforeEach
    public void setup() {
        originalPlaylist = new Playlist();
        originalPlaylist.setName("original playlist");

        Song s0 = new Song("Song 0");
        Song s1 = new Song("Song 1");
        Song s2 = new Song("Song 2");

        originalPlaylist.setSongs(new LinkedHashSet<>(Arrays.asList(s0, s1, s2)));
    }

    @Test
    public void givenShallowCopy_whenSourceChanged_thenCopyImpacted() {
        SharedPlaylist shallowCopy = new SharedPlaylist(originalPlaylist);
        shallowCopy.setName("shared playlist");
        assertSame("original playlist", originalPlaylist.getName());
        assertSame("shared playlist", shallowCopy.getName());

        Song s3 = new Song("Song 3");
        originalPlaylist.getSongs().add(s3);
        assertSame(originalPlaylist.getSongs(), shallowCopy.getSongs());
        assertTrue(shallowCopy.getSongs().contains(s3));
    }

}
