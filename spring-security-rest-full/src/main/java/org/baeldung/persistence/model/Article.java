package org.baeldung.persistence.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    protected User author;

    protected String title;

    @ElementCollection
    @CollectionTable(name = "TAGS")
    @OrderColumn
    @Column(name = "TAGNAME")
    protected Set<Tag> tags = new HashSet<>();

    public Article() {
    }

    public Article(User author, String title) {
        this.author = author;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) &&
            Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", author=" + author +
            ", title='" + title + '\'' +
            ", tags=" + tags +
            '}';
    }
}
