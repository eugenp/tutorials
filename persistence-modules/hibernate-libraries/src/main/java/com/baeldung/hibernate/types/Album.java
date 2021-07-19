package com.baeldung.hibernate.types;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Album")
@Table(name = "album")
public class Album extends BaseEntity {
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private CoverArt coverArt;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Song> songs;

    public CoverArt getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(CoverArt coverArt) {
        this.coverArt = coverArt;
    }


    public List<Song> getSongs() {
        return songs;
    }

    public void setSong(List<Song> songs) {
        this.songs = songs;
    }
}
