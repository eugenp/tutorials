package com.baeldung.spring.data.jpa.spel.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "articles")
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String language;

    public Article() {
    }

    public Article(final String title, final String content, final String language) {
        this.title = title;
        this.content = content;
        this.language = language;
    }

    public Article(final Long id, final String title, final String content, final String language) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "News{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", language=" + language +
               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Article article = (Article) o;

        if (!Objects.equals(id, article.id)) {
            return false;
        }
        if (!Objects.equals(title, article.title)) {
            return false;
        }
        if (!Objects.equals(content, article.content)) {
            return false;
        }
        return Objects.equals(language, article.language);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
