package com.baeldung.hoverfly;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.any;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.equalsTo;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.equalsToJson;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.equalsToXml;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.matches;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.startsWith;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.matchesJsonPath;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.matchesXPath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.rule.HoverflyRule;

public class HoverflyApiLiveTest {

    private static final SimulationSource source = dsl(service("http://www.baeldung.com").get("/api/courses/1").willReturn(success().body(jsonWithSingleQuotes("{'id':'1','name':'HCI'}")))

            .post("/api/courses").willReturn(success())

            .andDelay(3, TimeUnit.SECONDS).forMethod("POST"),

            service(matches("www.*dung.com")).get(startsWith("/api/student")).queryParam("page", any()).willReturn(success())

                    .post(equalsTo("/api/student")).body(equalsToJson(jsonWithSingleQuotes("{'id':'1','name':'Joe'}"))).willReturn(success())

                    .put("/api/student/1").body(matchesJsonPath("$.name")).willReturn(success())

                    .post("/api/student").body(equalsToXml("<student><id>2</id><name>John</name></student>")).willReturn(success())

                    .put("/api/student/2").body(matchesXPath("/student/name")).willReturn(success()));

    @ClassRule
    public static final HoverflyRule rule = HoverflyRule.inSimulationMode(source);
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void givenGetCourseById_whenRequestSimulated_thenAPICalledSuccessfully() throws URISyntaxException {
        final ResponseEntity<String> courseResponse = restTemplate.getForEntity("http://www.baeldung.com/api/courses/1", String.class);

        assertEquals(HttpStatus.OK, courseResponse.getStatusCode());
        assertEquals("{\"id\":\"1\",\"name\":\"HCI\"}", courseResponse.getBody());
    }

    @Test
    public void givenPostCourse_whenDelayInRequest_thenResponseIsDelayed() throws URISyntaxException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final ResponseEntity<Void> postResponse = restTemplate.postForEntity("http://www.baeldung.com/api/courses", null, Void.class);
        stopWatch.stop();
        long postTime = stopWatch.getTime();

        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(3L <= TimeUnit.MILLISECONDS.toSeconds(postTime));
    }

    @Test
    public void givenGetStudent_whenRequestMatcher_thenAPICalledSuccessfully() throws URISyntaxException {
        final ResponseEntity<Void> courseResponse = restTemplate.getForEntity("http://www.baeldung.com/api/student?page=3", Void.class);

        assertEquals(HttpStatus.OK, courseResponse.getStatusCode());
    }

    @Test
    public void givenPostStudent_whenBodyRequestMatcherJson_thenResponseContainsEqualJson() throws URISyntaxException {
        final ResponseEntity<Void> postResponse = restTemplate.postForEntity("http://www.baeldung.com/api/student", "{\"id\":\"1\",\"name\":\"Joe\"}", Void.class);

        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }

    @Test
    public void givenPutStudent_whenJsonPathMatcher_thenRequestJsonContainsElementInPath() throws URISyntaxException {
        RequestEntity<String> putRequest = RequestEntity.put(new URI("http://www.baeldung.com/api/student/1")).body("{\"id\":\"1\",\"name\":\"Trevor\"}");

        ResponseEntity<String> putResponse = restTemplate.exchange(putRequest, String.class);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
    }

    @Test
    public void givenPostStudent_whenBodyRequestMatcherXml_thenResponseContainsEqualXml() throws URISyntaxException {
        final ResponseEntity<Void> postResponse = restTemplate.postForEntity("http://www.baeldung.com/api/student", "<student><id>2</id><name>John</name></student>", Void.class);

        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }

    @Test
    public void givenPutStudent_whenXPathMatcher_thenRequestXmlContainsElementInXPath() throws URISyntaxException {
        RequestEntity<String> putRequest = RequestEntity.put(new URI("http://www.baeldung.com/api/student/2")).body("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<student><id>2</id><name>Monica</name></student>");

        ResponseEntity<String> putResponse = restTemplate.exchange(putRequest, String.class);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
    }
}
