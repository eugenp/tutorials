/*
 * (c) Центр ИТ, 2016. Все права защищены.
 */
package com.baeldung.querydsl.intro.entities;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String login;

    private Boolean disabled;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Set<BlogPost> blogPosts = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String name) {
        this.login = name;
    }

    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
