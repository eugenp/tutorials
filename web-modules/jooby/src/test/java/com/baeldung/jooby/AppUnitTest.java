package com.baeldung.jooby;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.jooby.MockRouter;

public class AppUnitTest {

    @Test
    public void given_defaultUrl_with_mockrouter_expect_fixedString() {
        MockRouter router = new MockRouter(new App());
        assertEquals("Hello World!", router.get("/")
            .value());
    }
}
