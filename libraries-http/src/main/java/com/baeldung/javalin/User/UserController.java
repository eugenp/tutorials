package com.baeldung.javalin.User;

import io.javalin.http.Handler;

import java.util.Objects;
import java.util.Optional;

public class UserController {
   public static Handler fetchAllUsernames = ctx -> {
        UserDao dao = UserDao.instance();
        Iterable<String> allUsers = dao.getAllUserNames();
        ctx.json(allUsers);
    };

    public static Handler fetchById = ctx ->{
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        UserDao dao = UserDao.instance();

        Optional<User> user = dao.getUserById(id);
        if(user == null){
            ctx.html("User Not Found");
        } else {
            ctx.json(user);
        }
    };
};

