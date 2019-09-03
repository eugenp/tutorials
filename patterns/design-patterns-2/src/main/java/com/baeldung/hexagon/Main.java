package com.baeldung.hexagon;

public class Main {
    public static void main(String[] args) {
        SongManagerPort songManagerPort = new InMemorySongManagerAdapter();

        songManagerPort.saveSong("In the end", "Linkin Park");

        System.out.println(songManagerPort.findSong("In the end"));
    }
}
