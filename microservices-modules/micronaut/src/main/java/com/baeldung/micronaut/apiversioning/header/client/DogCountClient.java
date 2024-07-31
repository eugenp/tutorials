package com.baeldung.micronaut.apiversioning.header.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.rxjava3.core.Flowable;

@Client("/")
@Header(name = HttpHeaders.ACCEPT, value = "application/json")
public interface DogCountClient {

    @Get(
        uri = "/dog/count",
        consumes = {"application/json"},
        produces = {"application/json"}
    )
    @Version("1")
    @SingleResult
    Flowable<String> countV1(@QueryValue("max") @Nullable Integer max);

    @Get(
        uri = "/dog/count",
        consumes = {"application/json"},
        produces = {"application/json"}
    )
    @Version("2")
    @SingleResult
    Flowable<String> countV2(@QueryValue("max") @Nullable Integer max);

    @Get(
        uri = "/dog/count",
        consumes = {"application/json"},
        produces = {"application/json"}
    )
    @SingleResult
    Flowable<String> countDefault(@QueryValue("max") @Nullable Integer max);
}
