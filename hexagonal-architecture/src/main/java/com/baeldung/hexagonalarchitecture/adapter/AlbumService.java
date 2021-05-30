package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.domain.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    AlbumServiceAdapter albumServiceAdapter;

    public List<Album> getAllAlbums() {
        return albumServiceAdapter.getAllAlbums();
    }

    public Album createAlbum(Album album) {
        return albumServiceAdapter.createAlbum(album);
    }
}
