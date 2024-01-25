package com.baeldung.listvsset.eager.set.fulldomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    private Long id;

    private String text;

    @ManyToOne
    private User author;

    @JsonBackReference
    @ManyToOne
    private Post post;

    public Comment() {
    }

    public Long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public User getAuthor() {
        return this.author;
    }

    public Post getPost() {
        return this.post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Comment comment = (Comment) o;

        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        return "Comment(id=" + this.getId() + ", text=" + this.getText() + ", author=" + this.getAuthor() + ", post=" + this.getPost()
               + ")";
    }
}
