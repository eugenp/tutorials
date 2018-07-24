package com.baeldung.reactivebackpressuredemo.repository;

import com.baeldung.reactivebackpressuredemo.model.Foo;

import reactor.core.publisher.Flux;

public interface FooTraffic {

    Flux<Foo> flowTraffic();

}
