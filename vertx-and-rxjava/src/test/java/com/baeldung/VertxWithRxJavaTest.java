package com.baeldung;

import io.reactivex.Flowable;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;
import io.vertx.reactivex.core.http.HttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

import static com.baeldung.MetaWeatherClient.getDataByPlaceId;
import static com.baeldung.MetaWeatherClient.searchByCityName;

public class VertxWithRxJavaTest {

    private Vertx vertx;
    private HttpClient httpClient;
    private FileSystem fileSystem;
    static Logger log = LoggerFactory.getLogger(VertxWithRxJavaTest.class);

    @Before public void setUp() {
        vertx = io.vertx.reactivex.core.Vertx.vertx();
        httpClient = vertx.createHttpClient();
        fileSystem = vertx.fileSystem();
    }

    @After public void tearDown() {
        vertx.close();
    }

    @Test public void lightLengthTest() throws InterruptedException {

        // read the file that contains one city name per line
        fileSystem
                .rxReadFile("cities.txt").toFlowable()
                .doOnNext(buffer -> log.info("File buffer ---\n{}\n---", buffer))

                // split file content in lines to obtain one city per line
                .flatMap(buffer -> Flowable.fromArray(buffer.toString().split("\\r?\\n")))
                .doOnNext(city -> log.info("City from file: '{}'", city))

                // discard cities that are commented out with a leading '#'
                .filter(city -> !city.startsWith("#"))
                .doOnNext(city -> log.info("City that survived filtering: '{}'", city))

                // for each city sends a request to obtain its 'woeid'
                // and collect the buffer from the answer
                .flatMap(city -> searchByCityName(httpClient, city) )
                .flatMap(response -> response.toFlowable())
                .doOnNext(buffer -> log.info("JSON of city detail: '{}'", buffer))

                // get the woeid of each city
                .map(cityBuffer -> cityBuffer
                        .toJsonArray()
                        .getJsonObject(0)
                        .getLong("woeid"))

                // use the id to ask for data
                .flatMap(cityId -> getDataByPlaceId(httpClient, cityId))
                .flatMap(response -> response
                        .toObservable()
                        .reduce(
                                Buffer.buffer(),
                                (total, newBuf) -> total.appendBuffer( newBuf )).toFlowable() )

                // get the JSON object out of the response
                .doOnNext(buffer -> log.info("JSON of place detail: '{}'", buffer))
                .map(buffer -> buffer.toJsonObject())

                // map the JSON in a POJO
                .map(json -> {
                    ZonedDateTime sunRise = ZonedDateTime.parse(json.getString("sun_rise"));
                    ZonedDateTime sunSet = ZonedDateTime.parse(json.getString("sun_set"));
                    String cityName = json.getString("title");
                    return new CityAndDayLength(
                        cityName,sunSet.toEpochSecond() - sunRise.toEpochSecond());
                })

                // consume the events
                .subscribe(
                    cityAndDayLength -> System.out.println(cityAndDayLength),
                    ex -> ex.printStackTrace());

        // enough to give time to complete the execution
        Thread.sleep(20000);

    }

}
