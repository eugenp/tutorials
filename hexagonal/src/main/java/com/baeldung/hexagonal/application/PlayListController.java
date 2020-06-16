package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.core.PlayList;
import com.baeldung.hexagonal.core.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayListController {

    @Autowired
    public PlayListServicePort playListServicePort;

    @GetMapping("playlist/{id}")
    public PlayList getPlayList(@PathVariable Long id) {
        return playListServicePort.getPlayList(id);
    }

    @PostMapping("playlist/{id}/song")
    public void addSong(@PathVariable Long id, @RequestBody Song song) {
        this.playListServicePort.addSong(id, song);
    }

    @DeleteMapping("playlist/{id}/song")
    public void removeSong(@PathVariable Long id, @RequestBody Song song) {
        this.playListServicePort.removeSong(id, song);
    }

}