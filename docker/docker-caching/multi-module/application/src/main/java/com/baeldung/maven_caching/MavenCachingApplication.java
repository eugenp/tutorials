package com.baeldung.maven_caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MavenCachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MavenCachingApplication.class, args);
        CoreClass cc = new CoreClass();
        System.out.println(cc.method());
    }

}
