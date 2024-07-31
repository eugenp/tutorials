package com.baeldung.dirtiescontext;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class UserCache {

    @Getter
    private Set<String> userList = new HashSet<>();

    public boolean addUser(String user) {
        return userList.add(user);
    }

    public boolean removeUser(String user) {
        return userList.remove(user);
    }

    public void printUserList(String message) {
        System.out.println(message + ": " + userList);
    }

}