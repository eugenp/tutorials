package com.baeldung.micronaut.apiversioning.header.server;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.reactivex.rxjava3.core.Flowable;

import java.util.stream.Stream;

@Controller("/dog")
public class DogCountController {

    @Get(value = "/count", produces = {"application/json"})
    @Version("1")
    public Flowable<String> countV1(@QueryValue("max") @Nullable Integer max) {
        return Flowable.fromStream(
            Stream.iterate(0, i -> i + 1)
                .map(index -> "Dog " + index)
                .limit(max == null ? 10 : max)
        );
    }

    @Get(value = "/count", produces = {"application/json"})
    @Version("2")
    public Flowable<String> countV2(@QueryValue("max") @NonNull Integer max) {
        return Flowable.fromStream(
                Stream.iterate(0, i -> i + 1)
                        .map(index -> "Dog " + index)
                        .limit(max)
        );
    }

}
