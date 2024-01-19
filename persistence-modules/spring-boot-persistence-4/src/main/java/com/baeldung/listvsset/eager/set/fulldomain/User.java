package com.baeldung.listvsset.eager.set.fulldomain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
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
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.EAGER)
    protected Set<Post> posts;


    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<Group> groups;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}