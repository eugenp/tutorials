package com.baeldung.maven_caching;

public class MavenCachingApplication {

    public static void main(String[] args) {
        CoreClass cc = new CoreClass();
        System.out.println(cc.method());
        System.out.println(cc.dependencyMethod());
    }

}
