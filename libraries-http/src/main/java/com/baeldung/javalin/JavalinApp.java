package com.baeldung.javalin;

import com.baeldung.javalin.User.UserController;
import io.javalin.Javalin;

public static void main(String[] args) {
        Javalin app = Javalin.create()
                .start(7000);
        app.get("/hello", ctx -> ctx.html("Hello, Javalin!"));

        app.get("/users", UserController.fetchAllUsernames);
        app.get("/users/{id}", UserController.fetchById);
        app.post("/", ctx -> {
            User user = ctx.bodyAsClass(User.class);
        });
    }
}
