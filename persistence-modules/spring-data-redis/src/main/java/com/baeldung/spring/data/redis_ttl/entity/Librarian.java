package com.baeldung.spring.data.redis_ttl.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="librarian", timeToLive = 5)
public class Librarian implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id private Long id;
    private String name;

    public Librarian() {
    }

    public Librarian(Long id, String name) {
        super();
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}