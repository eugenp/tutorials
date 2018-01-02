package com.baeldung.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String tweet;
    private String owner;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private Set<String> likes = new HashSet();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Tweet() {
    }

    public Tweet(String tweet, String owner) {
        this.tweet = tweet;
        this.owner = owner;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<String> getLikes() {
        return likes;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
    }

}
