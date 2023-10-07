package com.baeldung.micronaut.apiversioning.url.server;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.reactivex.rxjava3.core.Flowable;

import java.util.stream.Stream;

@Controller
public class SheepCountControllerV2 {

    @Get(
        uris = {"/v2/sheep/count", "/sheep/count"},
        consumes = {"application/json"},
        produces = {"application/json"}
    )
    Flowable<String> countV2(@QueryValue("max") @NonNull Integer max) {
        return Flowable.fromStream(
            Stream.iterate(0, i -> i + 1)
                    .map(index -> "Sheep " + index)
                    .limit(max)
        );
    }

}
