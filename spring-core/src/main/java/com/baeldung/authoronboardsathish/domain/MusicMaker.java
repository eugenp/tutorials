package com.baeldung.authoronboardsathish.domain;

import org.springframework.stereotype.Component;

@Component
public class MusicMaker {
    private ViolinMusicMaker violinMusicMaker;
    private PianoMusicMaker pianoMusicMaker;

    public void setPianoMusicMaker(PianoMusicMaker pianoMusicMaker) {
        this.pianoMusicMaker = pianoMusicMaker;
    }

    public MusicMaker(ViolinMusicMaker violinMusicMaker) {
        this.violinMusicMaker = violinMusicMaker;
    }

    @Override
    public String toString() {
        return "MusicMaker{" + "violinMusicMaker=" + violinMusicMaker + ", pianoMusicMaker=" + pianoMusicMaker + '}';
    }
}
