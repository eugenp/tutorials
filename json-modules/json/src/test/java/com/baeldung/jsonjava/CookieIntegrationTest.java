package com.baeldung.jsonjava;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.json.Cookie;
import org.json.JSONObject;
import org.junit.Test;

public class CookieIntegrationTest {

    @Test
    public void givenCookieString_thenConvertToJSONObject() {
        String cookie = "username=John Doe; expires=Thu, 18 Dec 2013 12:00:00 UTC; path=/";
        JSONObject cookieJO = Cookie.toJSONObject(cookie);

        assertThatJson(cookieJO)
          .isEqualTo("{\"path\":\"/\",\"expires\":\"Thu, 18 Dec 2013 12:00:00 UTC\",\"name\":\"username\",\"value\":\"John Doe\"}");
    }

    @Test
    public void givenJSONObject_thenConvertToCookieString() {
        JSONObject cookieJO = new JSONObject();
        cookieJO.put("name", "username");
        cookieJO.put("value", "John Doe");
        cookieJO.put("expires", "Thu, 18 Dec 2013 12:00:00 UTC");
        cookieJO.put("path", "/");

        String cookie = Cookie.toString(cookieJO);

        assertThat(cookie.split(";"))
          .containsExactlyInAnyOrder("username=John Doe", "path=/", "expires=Thu, 18 Dec 2013 12:00:00 UTC");
    }
}
