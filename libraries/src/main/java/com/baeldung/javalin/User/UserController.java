package com.baeldung.javalin.User;

import io.javalin.Handler;

public class UserController {
    public static Handler fetchAllUsernames = ctx -> {
        UserDao dao = UserDao.instance();
        Iterable<String> allUsers = dao.getAllUsernames();
        ctx.json(allUsers);
    };

    public static Handler fetchByUsername = ctx -> {
        String username = ctx.queryParam("username");
        UserDao dao = UserDao.instance();
        User user = dao.getUserByUsername(username);
        if (user == null) {
            ctx.html("Not Found");
        } else {
            ctx.json(user);
        }
    };
}
