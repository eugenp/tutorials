package com.baeldung.javalin;

import com.baeldung.javalin.User.UserController;
import io.javalin.Javalin;

public class JavalinApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create()
                .port(7000)
                .start();

        app.get("/hello", ctx -> ctx.html("Hello, Javalin!"));
        app.get("/users", UserController.fetchAllUsernames);
        app.get("/users/:id", UserController.fetchById);
    }
}
