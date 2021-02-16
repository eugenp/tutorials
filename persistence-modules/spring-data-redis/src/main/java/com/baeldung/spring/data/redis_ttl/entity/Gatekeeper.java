package com.baeldung.spring.data.redis_ttl.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class Gatekeeper implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id private Long id;
    private String name;

    public Gatekeeper(String name, String city) {
        super();
        this.name = name;
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