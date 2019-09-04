package com.baeldung.hexagon;

public class Main {
    public static void main(String[] args) {
        final SongServiceImpl songService = new SongServiceImpl(new InMemorySongManagerAdapter());

        songService.addSong("In the end", "Linkin Park");
    }
}
