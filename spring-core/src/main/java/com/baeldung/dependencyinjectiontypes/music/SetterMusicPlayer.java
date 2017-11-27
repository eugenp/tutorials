package com.baeldung.dependencyinjectiontypes.music;

public class SetterMusicPlayer {

    private Song songToPlay;

    public void setSongToPlay(Song songToPlay) {
        this.songToPlay = songToPlay;
    }

    public String play() {
        return songToPlay.getLyrics();
    }

}
