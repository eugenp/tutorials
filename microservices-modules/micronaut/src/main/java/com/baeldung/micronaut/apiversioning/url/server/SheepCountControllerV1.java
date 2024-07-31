package com.baeldung.micronaut.apiversioning.url.server;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.rxjava3.core.Flowable;

import java.util.stream.Stream;

@Controller("/v1/sheep/count")
public class SheepCountControllerV1 {

    @Get(
        uri = "{?max}",
        consumes = {"application/json"},
        produces = {"application/json"}
    )
    Flowable<String> countV1(@Nullable Integer max) {
        return Flowable.fromStream(
            Stream.iterate(0, i -> i + 1)
                .map(index -> "Sheep " + index)
                .limit(max == null ? 10 : max)
        );
    }

}
