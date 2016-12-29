package org.baeldung.web.controller.mediatypes;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class CustomMediaTypeControllerLiveTest {
    private static final String URL_PREFIX = "http://localhost:8082/spring-rest";
    private static final String CUSTOM_CONTENT_TYPE = "application/vnd.baeldung.api.v1+json";

    @Test
    public void getFor_customMediaType() {
        //given
        String url = URL_PREFIX + "/public/api/endpoint";

        //when
        Response response = RestAssured.get(url);

        //then
        assertEquals(200, response.getStatusCode());
        assertTrue(response.contentType().contains(CUSTOM_CONTENT_TYPE));

    }
}
