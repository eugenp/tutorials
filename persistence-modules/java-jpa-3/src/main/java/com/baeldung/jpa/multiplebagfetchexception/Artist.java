package com.baeldung.jpa.multiplebagfetchexception;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "artist")
    private List<Song> songs;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST)
    private List<Offer> offers;

    Artist(String name) {
        this.name = name;
        this.offers = new ArrayList<>();
    }

    void createOffer(String description) {
        this.offers.add(new Offer(description, this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Artist artist = (Artist) o;

        return Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    protected Artist() {
    }
}
