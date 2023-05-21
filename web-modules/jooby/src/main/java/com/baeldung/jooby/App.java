package com.baeldung.jooby;

import com.baeldung.jooby.bean.Employee;

import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.Session;
import io.jooby.SessionStore;

public class App extends Jooby {
    {
        setServerOptions(new ServerOptions().setPort(8080)
            .setSecurePort(8433));
    }

    {
        get("/", ctx -> "Hello World!");
    }

    {
        get("/user/{id}", ctx -> "Hello user : " + ctx.path("id")
            .value());
        get("/user/:id", ctx -> "Hello user: " + ctx.path("id")
            .value());
        get("/uid:{id}", ctx -> "Hello User with id : uid = " + ctx.path("id")
            .value());
    }

    {
        onStarting(() -> System.out.println("starting app"));

        onStop(() -> System.out.println("stopping app"));

        onStarted(() -> System.out.println("app started"));
    }

    {
        get("/login", ctx -> "Hello from Baeldung");
    }

    {
        post("/save", ctx -> {
            String userId = ctx.query("id")
                .value();
            return userId;
        });
    }

    {
        {
            assets("/employee", "public/form.html");
        }

        post("/submitForm", ctx -> {
            Employee employee = ctx.path(Employee.class);
            // ...
            return "employee data saved successfully";
        });

    }

    {
        decorator(next -> ctx -> {
            System.out.println("first");
            // Moves execution to next handler: second
            return next.apply(ctx);
        });
        decorator(next -> ctx -> {
            System.out.println("second");
            // Moves execution to next handler: third
            return next.apply(ctx);
        });

        get("/handler", ctx -> "third");
    }

    {
        get("/sessionInMemory", ctx -> {
            Session session = ctx.session();
            session.put("token", "value");
            return session.get("token")
                .value();
        });
    }

    {
        String secret = "super secret token";

        setSessionStore(SessionStore.signed(secret));

        get("/signedSession", ctx -> {
            Session session = ctx.session();
            session.put("token", "value");
            return session.get("token")
                .value();
        });
    }

    {
        get("/sessionInMemoryRedis", ctx -> {
            Session session = ctx.session();
            session.put("token", "value");
            return session.get("token")
                .value();
        });
    }

    /* This will work once redis is installed locally
    {
        install(new RedisModule("redis"));
        setSessionStore(new RedisSessionStore(require(RedisClient.class)));
        get("/redisSession", ctx -> {
            Session session = ctx.session();
            session.put("token", "value");
            return session.get("token");
        });
    }*/

    public static void main(final String[] args) {
        runApp(args, App::new);
    }

}
