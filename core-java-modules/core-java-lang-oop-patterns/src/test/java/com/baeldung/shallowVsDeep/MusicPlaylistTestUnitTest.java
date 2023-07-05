package com.baeldung.shallowVsDeep;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class MusicPlaylistTestUnitTest {

    @Test
    public void testReferenceCopy() {
        Song song1 = new Song("Song 1", "Artist 1");
        Song song2 = new Song("Song 2", "Artist 2");

        MusicPlaylist initialPlaylist = new MusicPlaylist("Playlist 1", List.of(song1, song2));
        MusicPlaylist referenceCopy = initialPlaylist;

        initialPlaylist.setPlaylistName("Playlist 1 changed");

        assertEquals(initialPlaylist.getPlaylistName(), referenceCopy.getPlaylistName());
        assertEquals(initialPlaylist, referenceCopy);
        assertSame(initialPlaylist, referenceCopy);
    }

    @Test
    public void testShallowCopy() {
        Song song1 = new Song("Song 1", "Artist 1");
        Song song2 = new Song("Song 2", "Artist 2");

        MusicPlaylist initialPlaylist = new MusicPlaylist("Playlist 1", List.of(song1, song2));
        MusicPlaylist shallowCopy = initialPlaylist.shallowCopy();

        initialPlaylist.setPlaylistName("Playlist 1 changed");
        initialPlaylist.getSongList()
            .get(0)
            .setSongName("Song 1 changed");
        shallowCopy.getSongList()
            .get(1)
            .setSongName("Song 2 changed");

        assertNotSame(initialPlaylist.getPlaylistName(), shallowCopy.getPlaylistName());
        assertSame(initialPlaylist.getSongList(), shallowCopy.getSongList());
        assertNotEquals(initialPlaylist, shallowCopy);
        assertNotSame(initialPlaylist, shallowCopy);
    }

    @Test
    public void testDeepCopy() {
        Song song1 = new Song("Song 1", "Artist 1");
        Song song2 = new Song("Song 2", "Artist 2");

        MusicPlaylist initialPlaylist = new MusicPlaylist("Playlist 1", List.of(song1, song2));
        MusicPlaylist deepCopy = new MusicPlaylist(initialPlaylist);

        initialPlaylist.setPlaylistName("Playlist 1 changed");
        initialPlaylist.getSongList()
            .get(0)
            .setSongName("Song 1 changed");
        deepCopy.getSongList()
            .get(1)
            .setSongName("Song 2 changed");

        assertNotSame(initialPlaylist.getPlaylistName(), deepCopy.getPlaylistName());
        assertNotSame(initialPlaylist.getSongList(), deepCopy.getSongList());
        assertNotEquals(initialPlaylist, deepCopy);
        assertNotSame(initialPlaylist, deepCopy);
    }
}