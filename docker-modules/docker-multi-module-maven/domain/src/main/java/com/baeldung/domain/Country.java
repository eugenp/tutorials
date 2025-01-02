package com.baeldung.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Country {

    @Id
    private Long id;
    private String iso;
    private String name;
    private String emoji;

    public Country(Long id, String iso, String name, String emoji) {
        this.id = id;
        this.iso = iso;
        this.name = name;
        this.emoji = emoji;
    }

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public String getIso() {
        return iso;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }
}
