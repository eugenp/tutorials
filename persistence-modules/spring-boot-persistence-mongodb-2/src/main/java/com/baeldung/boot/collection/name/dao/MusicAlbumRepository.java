package com.baeldung.boot.collection.name.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.collection.name.data.MusicAlbum;

public interface MusicAlbumRepository extends MongoRepository<MusicAlbum, String> {

}
