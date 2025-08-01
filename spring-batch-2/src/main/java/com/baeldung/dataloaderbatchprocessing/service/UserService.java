package com.baeldung.dataloaderbatchprocessing.service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.baeldung.dataloaderbatchprocessing.entity.User;

public class UserService {

    public CompletableFuture<List<User>> getUsersByIds(List<String> ids) {

        return CompletableFuture.supplyAsync(() -> {
            List<User> users = new ArrayList<>();
            for (String id : ids) {
                users.add(new User(id, "User_" + id));
            }
            return users;
        });
    }
}
