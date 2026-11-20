package com.baeldung.sonarqubeandjacoco.transientorserializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private String username;
    private Address address;
    private transient List<String> temporaryCache;

    public User(String username, Address address) {
        this.username = username;
        this.address = address;
        this.temporaryCache = new ArrayList<>();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.temporaryCache = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getTemporaryCache() {
        return temporaryCache;
    }

    public void setTemporaryCache(List<String> temporaryCache) {
        this.temporaryCache = temporaryCache;
    }
}
