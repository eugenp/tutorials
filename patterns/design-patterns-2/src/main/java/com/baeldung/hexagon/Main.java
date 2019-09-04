package com.baeldung.hexagon;

public class Main {
    public static void main(String[] args) {
        final SongServiceAdapter songService = new SongServiceAdapter(new SongStorageInMemoryAdapter());

        songService.addSong("In the end", "Linkin Park");
        songService.findSong("In the end");
    }
}
