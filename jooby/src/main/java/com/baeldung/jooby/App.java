package com.baeldung.jooby;

import org.jooby.Jooby;
import org.jooby.Mutant;
import org.jooby.Session;
import org.jooby.jedis.Redis;
import org.jooby.jedis.RedisSessionStore;

import com.baeldung.jooby.bean.Employee;

public class App extends Jooby {

	{
		port(8080);
		securePort(8443);
	}

	{
		get("/", () -> "Hello World!");
	}

	{
		get("/user/{id}", req -> "Hello user : " + req.param("id").value());
		get("/user/:id", req -> "Hello user: " + req.param("id").value());
		get("/uid:{id}", req -> "Hello User with id : uid" + req.param("id").value());
	}

	{
		onStart(() -> {
			System.out.println("starting app");
		});

		onStop(() -> {
			System.out.println("stopping app");
		});

		onStarted(() -> {
			System.out.println("app started");
		});
	}

	{
		get("/login", () -> "Hello from Baeldung");
	}

	{
		post("/save", req -> {
			Mutant token = req.param("token");
			return token.intValue();
		});
	}

	{
		{
			assets("/employee", "form.html");
		}

		post("/submitForm", req -> {
			Employee employee = req.params(Employee.class);
			// TODO
			return "empoyee data saved successfullly";
		});
	}

	{
		get("/filter", (req, resp, chain) -> {
			// TODO
			// resp.send(...);
			chain.next(req, resp);
		});
		get("/filter", (req, resp) -> {
			resp.send("filter response");
		});
	}

	{
//		cookieSession();
		
//		use(new Redis());
//
//		session(RedisSessionStore.class);

		get("/session", req -> {
			Session session = req.session();
			session.set("token", "value");
			return session.get("token").value();
		});
	}

	public static void main(final String[] args) {

		run(App::new, args);
	}

}
