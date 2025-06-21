package com.baeldung.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Article extends PanacheEntity {

    public String title;
    public String content;
    public String status;

    public Article() {
    }

    public Article(String title, String content, String status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }
}

