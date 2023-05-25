package com.baeldung.boot.collection.name.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.collection.name.data.Compilation;
import com.baeldung.boot.collection.name.data.MusicAlbum;
import com.baeldung.boot.collection.name.data.MusicTrack;
import com.baeldung.boot.collection.name.data.Store;
import com.baeldung.boot.collection.name.service.MusicStoreService;

@RestController
@RequestMapping("/music")
public class MusicStoreController {
    @Autowired
    private MusicStoreService service;

    @PostMapping("/album")
    public MusicAlbum post(@RequestBody MusicAlbum item) {
        return service.add(item);
    }

    @GetMapping("/album")
    public List<MusicAlbum> getAlbumList() {
        return service.getAlbumList();
    }

    @PostMapping("/compilation")
    public Compilation post(@RequestBody Compilation item) {
        return service.add(item);
    }

    @GetMapping("/compilation")
    public List<Compilation> getCompilationList() {
        return service.getCompilationList();
    }

    @PostMapping("/store")
    public Store post(@RequestBody Store item) {
        return service.add(item);
    }

    @GetMapping("/store")
    public List<Store> getStoreList() {
        return service.getStoreList();
    }

    @PostMapping("/track")
    public MusicTrack post(@RequestBody MusicTrack item) {
        return service.add(item);
    }

    @GetMapping("/track")
    public List<MusicTrack> getTrackList() {
        return service.getTrackList();
    }
}
