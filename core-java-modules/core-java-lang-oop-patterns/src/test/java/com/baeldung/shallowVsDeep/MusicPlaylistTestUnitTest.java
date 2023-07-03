package com.baeldung.shallowVsDeep;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class MusicPlaylistTestUnitTest {

    @Test
    public void testReferenceCopy() {
        // Creating initial objects
        Song song1 = new Song("Song 1", "Artist 1");
        Song song2 = new Song("Song 2", "Artist 2");

        MusicPlaylist initialPlaylist = new MusicPlaylist("Playlist 1", List.of(song1, song2));

        // REFERENCE COPY
        MusicPlaylist referenceCopy = initialPlaylist;

        System.out.println("Initial playlist reference: " + initialPlaylist);
        System.out.println("Reference copy reference: " + referenceCopy);

        // Changing initial object will also reflect in reference copy
        initialPlaylist.setPlaylistName("Playlist 1 changed");

        assertEquals(initialPlaylist.getPlaylistName(), referenceCopy.getPlaylistName());
        assertEquals(initialPlaylist, referenceCopy); //uses equals method from MusicPlaylist class
        assertSame(initialPlaylist, referenceCopy); //uses == operator (reference in memory)
    }

//    @Test
//    public void testShallowCopy() {
//        // Creating initial objects
//        Song song1 = new Song("Song 1", "Artist 1");
//        Song song2 = new Song("Song 2", "Artist 2");
//
//        MusicPlaylist initialPlaylist = new MusicPlaylist("Playlist 1", List.of(song1, song2));
//
//        // SHALLOW COPY
//        MusicPlaylist shallowCopy = new MusicPlaylist(initialPlaylist); //uses shallow copy constructor
//
//        initialPlaylist.setPlaylistName("Playlist 1 changed"); // changes only a field (not reflected)
//        initialPlaylist.getSongList()
//            .get(0)
//            .setSongName("Song 1 changed"); // changes a field in an object in a list
//        shallowCopy.getSongList()
//            .get(1)
//            .setSongName("Song 2 changed"); // changes a field in an object in a list
//
//        assertNotSame(initialPlaylist.getPlaylistName(), shallowCopy.getPlaylistName());
//
//        assertSame(initialPlaylist.getSongList(), shallowCopy.getSongList());
//
//        assertNotEquals(initialPlaylist, shallowCopy);
//        assertNotSame(initialPlaylist, shallowCopy);
//    }

    @Test
    public void testDeepCopy() {
        // Creating initial objects
        Song song1 = new Song("Song 1", "Artist 1");
        Song song2 = new Song("Song 2", "Artist 2");

        MusicPlaylist initialPlaylist = new MusicPlaylist("Playlist 1", List.of(song1, song2));

        // DEEP COPY
        MusicPlaylist deepCopy = new MusicPlaylist(initialPlaylist); //uses deep copy constructor

        initialPlaylist.setPlaylistName("Playlist 1 changed"); // changes only a field (not reflected)
        initialPlaylist.getSongList()
            .get(0)
            .setSongName("Song 1 changed"); // changes a field in an object in a list
        deepCopy.getSongList()
            .get(1)
            .setSongName("Song 2 changed"); // changes a field in an object in a list

        assertNotSame(initialPlaylist.getPlaylistName(), deepCopy.getPlaylistName());

        assertNotSame(initialPlaylist.getSongList(), deepCopy.getSongList());

        assertNotEquals(initialPlaylist, deepCopy);
        assertNotSame(initialPlaylist, deepCopy);
    }
}