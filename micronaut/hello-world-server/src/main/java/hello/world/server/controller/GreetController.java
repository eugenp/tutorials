package hello.world.server.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/greet")
public class GreetController {

    private String greeting = "Hello, ";

    @Get("/{name}")
    public String greet(String name) {
        return greeting + name;
    }

    @Post
    public void setGreeting(@Body String greeting)
    {
        this.greeting = greeting;
    }
}
