package com.baeldung.jpa.multiplebagfetchexception;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @OrderColumn(name = "arrangement_index")
    private List<FavoriteSong> favoriteSongs;

    @ManyToMany
    private Set<Album> followingAlbums;

    User(String name) {
        this.name = name;
        this.playlists = new ArrayList<>();
        this.favoriteSongs = new ArrayList<>();
        this.followingAlbums = new HashSet<>();
    }

    void followAlbum(Album album) {
        this.followingAlbums.add(album);
    }

    void createPlaylist(String name) {
        this.playlists.add(new Playlist(name, this));
    }

    void addSongToFavorites(Song song) {
        this.favoriteSongs.add(new FavoriteSong(song, this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    protected User() {
    }
}
