package com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@Table(name = "ARTICLE")
public class Article {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    @NotEmpty(message = "Property name is required")
    @Size(min = 1, max = 64, message = "Property name, must be (1-64) chars length")
    private String name;

    @Column(name = "EDITOR")
    @NotEmpty(message = "Property editor is required")
    @Size(min = 1, max = 64, message = "Property editor, must be (1-64) chars length")
    private String editor;

    @Column(name = "CONTENT")
    @NotEmpty(message = "Property content is required")
    private String content;

    public Article() {
    }

    public Article(Long id, String name, String editor, String content) {
        this.id = id;
        this.name = name;
        this.editor = editor;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
