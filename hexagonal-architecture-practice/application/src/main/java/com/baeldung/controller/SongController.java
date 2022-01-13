package com.baeldung.controller;

import com.baeldung.data.Song;
import com.baeldung.ports.api.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    public Song addSong(@RequestBody Song song){
        return songService.save(song);
    }
}
