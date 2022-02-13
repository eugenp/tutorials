package com.baeldung.maven_caching;

import com.google.common.io.Files;

public class MavenCachingMain {

    public static void main(String[] args) {
		System.out.println("Hello from maven_caching app!!!");
        System.out.println(Files.simplifyPath("/home/app/test"));
    }
}
