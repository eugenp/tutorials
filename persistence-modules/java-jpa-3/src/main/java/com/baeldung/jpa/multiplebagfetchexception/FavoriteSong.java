package com.baeldung.jpa.multiplebagfetchexception;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
class FavoriteSong {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Song song;

    @ManyToOne
    private User user;

    @Column(name = "arrangement_index", nullable = false)
    private int arrangementIndex;

    FavoriteSong(Song song, User user) {
        this.song = song;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FavoriteSong likedSong = (FavoriteSong) o;

        return Objects.equals(id, likedSong.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    protected FavoriteSong() {
    }

}
