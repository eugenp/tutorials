package com.baeldung.web;

import static com.baeldung.Consts.APPLICATION_PORT;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.common.web.AbstractBasicLiveTest;
import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.ConfigIntegrationTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigIntegrationTest.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class FooPageableLiveTest extends AbstractBasicLiveTest<Foo> {

    public FooPageableLiveTest() {
        super(Foo.class);
    }

    // API

    @Override
    public final void create() {
        super.create(new Foo(randomAlphabetic(6)));
    }

    @Override
    public final String createAsUri() {
        return createAsUri(new Foo(randomAlphabetic(6)));
    }

    @Override
    @Test
    public void whenResourcesAreRetrievedPaged_then200IsReceived() {
        this.create();

        final Response response = RestAssured.get(getPageableURL() + "?page=0&size=10");

        assertThat(response.getStatusCode(), is(200));
    }

    @Override
    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        final String url = getPageableURL() + "?page=" + randomNumeric(5) + "&size=10";
        final Response response = RestAssured.get(url);

        assertThat(response.getStatusCode(), is(404));
    }

    @Override
    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        create();

        final Response response = RestAssured.given()
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .get(getPageableURL() + "?page=0&size=10");

        assertFalse(response.body().as(List.class).isEmpty());
    }

    protected String getPageableURL() {
        return getURL() + "/pageable";
    }

}
