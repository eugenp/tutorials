package com.baeldung.hibernate.ogm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Author {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String authorId;

    private String authorName;

    @ManyToOne
    private Editor editor;

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    private Set<Article> authoredArticles = new HashSet<>();

    // constructors, getters and setters...

    Author() {
    }

    public Author(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Set<Article> getAuthoredArticles() {
        return authoredArticles;
    }

    public void setAuthoredArticles(Set<Article> authoredArticles) {
        this.authoredArticles = authoredArticles;
    }
}