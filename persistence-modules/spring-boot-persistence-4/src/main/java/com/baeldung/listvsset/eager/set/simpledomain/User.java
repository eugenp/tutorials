package com.baeldung.listvsset.eager.set.simpledomain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;

@Data
@Entity(name = "simple_user")
@Table(name = "simple_user")
public class User {

    @Id
    private Long id;
    private String username;
    private String email;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.EAGER)
    protected Set<Post> posts;

}