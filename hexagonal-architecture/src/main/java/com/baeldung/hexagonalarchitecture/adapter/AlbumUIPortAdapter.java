package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.domain.Album;
import com.baeldung.hexagonalarchitecture.adapter.AlbumService;
import com.baeldung.hexagonalarchitecture.ports.AlbumUIPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/albums/")
public class AlbumUIPortAdapter implements AlbumUIPort {

    @Autowired
    private AlbumService albumService;

    @Override
    public Album createAlbum(Album album) {
        return albumService.createAlbum(album);
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }
}
