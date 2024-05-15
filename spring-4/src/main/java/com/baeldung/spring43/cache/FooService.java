package com.baeldung.spring43.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FooService {

    @Cacheable(cacheNames = "foos", sync = true)
    public Foo getFoo(String id) {
        return new Foo();
    }

}
