package com.baeldung.listvsset.eager.list.fulldomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
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

}
