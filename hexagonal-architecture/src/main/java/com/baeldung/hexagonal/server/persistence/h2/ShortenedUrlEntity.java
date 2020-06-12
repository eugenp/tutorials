package com.baeldung.hexagonal.server.persistence.h2;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShortenedUrlEntity {

    @Id
    private String code;
    private String url;

    public ShortenedUrlEntity() {
    }

    public ShortenedUrlEntity(String code, String url) {
        this.code = code;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }
}
