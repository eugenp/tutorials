package com.baeldung.architecture.hexa;

public class Main {

    static ReversorPort r = new Reversor();

    public static void main(String[] args) {
        String reverse = r.reverse("Hello world.");
        System.out.println(reverse);
    }
}
