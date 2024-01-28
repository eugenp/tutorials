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

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

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

    public String toString() {
        return "User(id=" + this.getId() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ", profile="
               + this.getProfile() + ", posts=" + this.getPosts() + ", groups=" + this.getGroups() + ")";
    }
}