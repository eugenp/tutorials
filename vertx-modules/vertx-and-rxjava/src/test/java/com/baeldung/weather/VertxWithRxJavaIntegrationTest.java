package com.baeldung.weather;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

import static com.baeldung.weather.MetaWeatherClient.getDataByPlaceId;
import static com.baeldung.weather.MetaWeatherClient.searchByCityName;

public class VertxWithRxJavaIntegrationTest {

    private Vertx vertx;
    private HttpClient httpClient;
    private FileSystem fileSystem;
    private static Logger log = LoggerFactory.getLogger(VertxWithRxJavaIntegrationTest.class);

    @Before
    public void setUp() {
        vertx = Vertx.vertx();
        httpClient = vertx.createHttpClient();
        fileSystem = vertx.fileSystem();
    }

    @After
    public void tearDown() {
        vertx.close();
    }

    @Test
    public void shouldDisplayLightLenghts() throws InterruptedException {

        // read the file that contains one city name per line
        fileSystem
          .rxReadFile("cities.txt").toFlowable()
            .doOnNext(buffer -> log.info("File buffer ---\n{}\n---", buffer))
          .flatMap(buffer -> Flowable.fromArray(buffer.toString().split("\\r?\\n")))
            .doOnNext(city -> log.info("City from file: '{}'", city))
          .filter(city -> !city.startsWith("#"))
            .doOnNext(city -> log.info("City that survived filtering: '{}'", city))
          .flatMap(city -> searchByCityName(httpClient, city))
          .flatMap(HttpClientResponse::toFlowable)
            .doOnNext(buffer -> log.info("JSON of city detail: '{}'", buffer))
          .map(extractingWoeid())
          .flatMap(cityId -> getDataByPlaceId(httpClient, cityId))
          .flatMap(toBufferFlowable())
            .doOnNext(buffer -> log.info("JSON of place detail: '{}'", buffer))
          .map(Buffer::toJsonObject)
          .map(toCityAndDayLength())
          .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(20000);  // enough to give time to complete the execution
    }

    private static Function<HttpClientResponse, Publisher<? extends Buffer>> toBufferFlowable() {
        return response -> response
          .toObservable()
          .reduce(
            Buffer.buffer(),
            Buffer::appendBuffer).toFlowable();
    }

    private static Function<Buffer, Long> extractingWoeid() {
        return cityBuffer -> cityBuffer
          .toJsonArray()
          .getJsonObject(0)
          .getLong("woeid");
    }

    private static Function<JsonObject, CityAndDayLength> toCityAndDayLength() {
        return json -> {
            ZonedDateTime sunRise = ZonedDateTime.parse(json.getString("sun_rise"));
            ZonedDateTime sunSet = ZonedDateTime.parse(json.getString("sun_set"));
            String cityName = json.getString("title");
            return new CityAndDayLength(
              cityName, sunSet.toEpochSecond() - sunRise.toEpochSecond());
        };
    }

}
