package com.baeldung.dependencyinjectiontypes.music;

public class ConstructorMusicPlayer {

    private Song songToPlay;

    public ConstructorMusicPlayer(Song songToPlay) {
        this.songToPlay = songToPlay;
    }

    public String play() {
        return songToPlay.getLyrics();
    }

}
