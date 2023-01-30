package com.baeldung.object.copy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeepCloneablePlaylistUnitTest {

    DeepCloneablePlaylist originalPlaylist;

    @BeforeEach
    public void setup() {
        originalPlaylist = new DeepCloneablePlaylist();
        originalPlaylist.setName("original playlist");

        Song s0 = new Song("Song 0");
        Song s1 = new Song("Song 1");
        Song s2 = new Song("Song 2");

        originalPlaylist.setSongs(new LinkedHashSet<>(Arrays.asList(s0, s1, s2)));
    }

    @Test
    public void givenDeepCopy_whenSourceChanged_thenCopyNotImpacted() throws Exception {
        Playlist deepCopy = originalPlaylist.clone();
        deepCopy.setName("deep copy playlist");
        assertSame("original playlist", originalPlaylist.getName());
        assertSame("deep copy playlist", deepCopy.getName());

        Song s3 = new Song("Song 3");
        originalPlaylist.getSongs().add(s3);
        assertNotSame(originalPlaylist.getSongs(), deepCopy.getSongs());
        assertFalse(deepCopy.getSongs().contains(s3));
    }
}
