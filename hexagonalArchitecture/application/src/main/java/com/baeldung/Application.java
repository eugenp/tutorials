package com.baeldung;

import com.baeldung.impl.*;

public class Application {
    public static void main(String[] args) {
        new Domain(new OutputStreamingPortConsoleAdapter(), new NamingPortCurrentLoginUserAdapter(),
                new GreetingPortStaticAdapter(), new TranslatingPortMapAdapter()).greeting();
    }
}
