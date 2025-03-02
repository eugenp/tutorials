package com.baeldung.enumquery.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String title;

    private String author;

    @Enumerated(EnumType.STRING)
    private ArticleStage stage;

}
