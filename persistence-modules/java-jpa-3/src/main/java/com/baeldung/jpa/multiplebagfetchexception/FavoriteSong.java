package com.baeldung.jpa.multiplebagfetchexception;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
class FavoriteSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
