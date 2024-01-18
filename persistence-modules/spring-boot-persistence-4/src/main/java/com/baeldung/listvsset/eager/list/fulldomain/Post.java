package com.baeldung.listvsset.eager.list.fulldomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    private Long id;

    @Lob
    private String content;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @JsonBackReference
    @ManyToOne
    private User author;
}
