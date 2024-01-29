package com.baeldung.listvsset.eager.set.fulldomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Post {

    @Id
    private Long id;

    @Lob
    private String content;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @JsonBackReference
    @ManyToOne
    private User author;

    public Post() {
    }

    public Long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Post post = (Post) o;

        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        return "Post(id=" + this.getId() + ", content=" + this.getContent() + ", comments=" + this.getComments() + ", author="
               + this.getAuthor() + ")";
    }
}
