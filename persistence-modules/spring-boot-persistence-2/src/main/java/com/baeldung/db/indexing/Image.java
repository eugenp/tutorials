package com.baeldung.db.indexing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
class Image {

    @Id
    @GeneratedValue
    Long id;

    String name;

    String location;

    @Lob
    byte[] content;

    public Image() {
    }

    public Image(Long id) {
        this.id = id;
    }

    public Image(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
