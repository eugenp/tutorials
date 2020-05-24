package com.baeldung.partialupdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication 
@EnableCaching
public class PartialUpdateApplication {

    @Autowired 
    CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(PartialUpdateApplication.class, args);
    }

}
