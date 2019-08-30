package com.baeldung;

import com.baeldung.impl.NamingPortStaticAdapter;
import com.baeldung.impl.OutputStreamingPortConsoleAdapter;

public class Application {
    public static void main(String[] args) {
        new Domain(new OutputStreamingPortConsoleAdapter(), new NamingPortStaticAdapter()).greeting();
    }
}
