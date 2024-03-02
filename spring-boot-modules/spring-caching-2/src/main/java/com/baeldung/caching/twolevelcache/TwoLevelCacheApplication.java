package com.baeldung.caching.twolevelcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TwoLevelCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoLevelCacheApplication.class, args);
    }
}