package com.baeldung.micronaut.apiversioning.param.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.rxjava3.core.Flowable;

@Client("/")
@Header(name = HttpHeaders.ACCEPT, value = "application/json")
public interface CatCountClient {

    @Get(
        uri = "/cat/count",
        consumes = {"application/json"},
        produces = {"application/json"}
    )
    @SingleResult
    Flowable<String> count(@QueryValue("max") @Nullable Integer max, @QueryValue(value = "v", defaultValue = "1") @Nullable Integer version);
}
