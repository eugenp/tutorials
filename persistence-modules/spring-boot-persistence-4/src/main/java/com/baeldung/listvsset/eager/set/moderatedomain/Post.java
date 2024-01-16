package com.baeldung.listvsset.eager.set.moderatedomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    private Long id;

    @Lob
    private String content;

    @JsonBackReference
    @ManyToOne
    private User author;

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
}
