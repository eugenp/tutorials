package com.baeldung.boot.collection.name.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("#{@naming.fix('MusicTrack')}")
public class MusicTrack {
    @Id
    private String id;

    private String name;

    private String artist;

    public MusicTrack() {
    }

    public MusicTrack(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public MusicTrack(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
