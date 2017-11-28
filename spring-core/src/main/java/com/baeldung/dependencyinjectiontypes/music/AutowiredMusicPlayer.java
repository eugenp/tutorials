package com.baeldung.dependencyinjectiontypes.music;

import org.springframework.beans.factory.annotation.Autowired;

public class AutowiredMusicPlayer {

    @Autowired
    private Song songToPlay;

    public String play() {
        return songToPlay.getLyrics();
    }

}
