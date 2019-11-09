package com.baeldung.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;
import java.util.List;

@JsonIdentityInfo(generator = JSOGGenerator.class)

@NodeEntity
public class Movie {
    @Id @GeneratedValue
    Long id;

    private String title;

    private int released;
    private String tagline;

    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<Role> roles;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public int getReleased() {
        return released;
    }

    public String getTagline() {
        return tagline;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
