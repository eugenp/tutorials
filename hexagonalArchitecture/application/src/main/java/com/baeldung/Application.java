package com.baeldung;

import com.baeldung.impl.GreetingPortStaticAdapter;
import com.baeldung.impl.NamingPortStaticAdapter;
import com.baeldung.impl.OutputStreamingPortConsoleAdapter;
import com.baeldung.impl.TranslatingPortMapAdapter;

public class Application {
    public static void main(String[] args) {
        new Domain(new OutputStreamingPortConsoleAdapter(), new NamingPortStaticAdapter(),
                new GreetingPortStaticAdapter(), new TranslatingPortMapAdapter()).greeting();
    }
}
