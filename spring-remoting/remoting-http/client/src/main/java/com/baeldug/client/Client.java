package com.baeldug.client;

import com.baeldung.api.GreetingsService;

public class Client {

    private GreetingsService greetingsService;

    public Client(GreetingsService greetingsService) {
        this.greetingsService = greetingsService;
    }

    public void run() {
        System.out.println( greetingsService.sayHello() );
    }

}
