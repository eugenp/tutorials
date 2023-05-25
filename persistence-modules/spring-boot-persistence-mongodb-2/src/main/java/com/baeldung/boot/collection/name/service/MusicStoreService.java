package com.baeldung.boot.collection.name.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.boot.collection.name.dao.CompilationRepository;
import com.baeldung.boot.collection.name.dao.MusicAlbumRepository;
import com.baeldung.boot.collection.name.dao.MusicTrackRepository;
import com.baeldung.boot.collection.name.dao.StoreRepository;
import com.baeldung.boot.collection.name.data.Compilation;
import com.baeldung.boot.collection.name.data.MusicAlbum;
import com.baeldung.boot.collection.name.data.MusicTrack;
import com.baeldung.boot.collection.name.data.Store;

@Service
public class MusicStoreService {
    @Autowired
    private MusicAlbumRepository albumRepository;

    @Autowired
    private CompilationRepository compilationRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MusicTrackRepository trackRepository;

    public MusicAlbum add(MusicAlbum item) {
        return albumRepository.save(item);
    }

    public List<MusicAlbum> getAlbumList() {
        return albumRepository.findAll();
    }

    public Compilation add(Compilation item) {
        return compilationRepository.save(item);
    }

    public List<Compilation> getCompilationList() {
        return compilationRepository.findAll();
    }

    public Store add(Store item) {
        return storeRepository.save(item);
    }

    public List<Store> getStoreList() {
        return storeRepository.findAll();
    }

    public MusicTrack add(MusicTrack item) {
        return trackRepository.save(item);
    }

    public List<MusicTrack> getTrackList() {
        return trackRepository.findAll();
    }
}
