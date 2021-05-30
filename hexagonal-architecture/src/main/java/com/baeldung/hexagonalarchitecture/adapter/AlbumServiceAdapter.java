package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.domain.Album;
import com.baeldung.hexagonalarchitecture.ports.AlbumRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceAdapter  {

    @Autowired
    AlbumRepositoryPort albumRepositoryPort;

    public List<Album> getAllAlbums() {
        return albumRepositoryPort.findAll();
    }

    public Album createAlbum(Album album) {
        return albumRepositoryPort.save(album);
    }
}
