package com.baeldung.jpa.multiplebagfetchexception;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
