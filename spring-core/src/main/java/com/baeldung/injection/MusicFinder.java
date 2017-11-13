package com.baeldung.injection;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MusicFinder {

    public List<Music> find(Long userId) {
        return Arrays.asList(new Music("Alive", "Pearl Jam"), new Music("Man in the Box", "Alice in Chains"), new Music("Nevermind", "Nirvana"));
    }

}
