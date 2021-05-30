package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Album;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AlbumUIPort {

    @PostMapping("create")
    Album createAlbum(@RequestBody Album album);

    @GetMapping("all")
    List<Album> getAllAlbums();
}
