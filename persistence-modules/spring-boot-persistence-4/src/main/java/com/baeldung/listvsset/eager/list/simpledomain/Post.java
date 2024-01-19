package com.baeldung.listvsset.eager.list.simpledomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
}
