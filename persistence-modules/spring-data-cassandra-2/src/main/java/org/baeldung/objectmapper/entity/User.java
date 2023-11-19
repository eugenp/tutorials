package org.baeldung.objectmapper.entity;

import com.datastax.oss.driver.api.mapper.annotations.*;

@Entity
@CqlName("user_profile")
public class User {
    @PartitionKey
    private int id;
    @CqlName("username")
    private String userName;
    @ClusteringColumn
    private int userAge;

    @Computed("writetime(userName)")
    private long writetime;

    public User() {
    }

    public User(int id, String userName, int userAge) {
        this.id = id;
        this.userName = userName;
        this.userAge = userAge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public long getWritetime() {
        return writetime;
    }

    public void setWritetime(long writetime) {
        this.writetime = writetime;
    }
}