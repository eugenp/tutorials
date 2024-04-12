package com.baeldung.boot.collection.name.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.collection.name.data.MusicTrack;

public interface MusicTrackRepository extends MongoRepository<MusicTrack, String> {

}
