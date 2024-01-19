package com.baeldung.listvsset.eager.list.fulldomain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
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
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.EAGER)
    protected List<Post> posts;


    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Group> groups;
}