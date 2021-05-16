package com.baeldung.jpa.multiplebagfetchexception;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
