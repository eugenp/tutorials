package com.baeldung.weather;

import io.reactivex.Flowable;
import io.vertx.core.http.RequestOptions;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpClientRequest;
import io.vertx.reactivex.core.http.HttpClientResponse;

import static java.lang.String.format;

class MetaWeatherClient {

    private static RequestOptions metawether = new RequestOptions()
      .setHost("www.metaweather.com")
      .setPort(443)
      .setSsl(true);

    /**
     * @return A flowable backed by vertx that automatically sends an HTTP request at soon as the first subscription is received.
     */
    private static Flowable<HttpClientResponse> autoPerformingReq(HttpClient httpClient, String uri) {
        HttpClientRequest req = httpClient.get(new RequestOptions(metawether).setURI(uri));
        return req.toFlowable()
          .doOnSubscribe(subscription -> req.end());
    }

    static Flowable<HttpClientResponse> searchByCityName(HttpClient httpClient, String cityName) {
        HttpClientRequest req = httpClient.get(
          new RequestOptions()
            .setHost("www.metaweather.com")
            .setPort(443)
            .setSsl(true)
            .setURI(format("/api/location/search/?query=%s", cityName)));
        return req
          .toFlowable()
          .doOnSubscribe(subscription -> req.end());
    }

    static Flowable<HttpClientResponse> getDataByPlaceId(HttpClient httpClient, long placeId) {
        return autoPerformingReq(
          httpClient,
          format("/api/location/%s/", placeId));
    }

}
