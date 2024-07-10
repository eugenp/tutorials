package com.baeldung.lombok.intro;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

public class LoginResultLiveTest {

    @Test
    public void givenAnnotatedLoginResult_thenHasConstructorForAllFinalFields()
    throws Exception {
        /* LoginResult loginResult = */ new LoginResult(
                Instant.now(),
                "apitoken",
                Duration.ofHours(1),
                new URI("https://api.product.com/token-refresh").toURL());
    }

    @Test
    public void givenAnnotatedLoginResult_thenHasFluentGetters()
    throws Exception {
        Instant loginTs = Instant.now();
        LoginResult loginResult = new LoginResult(
                loginTs,
                "apitoken",
                Duration.ofHours(1),
                new URI("https://api.product.com/token-refresh").toURL());

        Assert.assertEquals(loginResult.loginTs(), loginTs);
        Assert.assertEquals(loginResult.authToken(), "apitoken");
        Assert.assertEquals(loginResult.tokenValidity(), Duration.ofHours(1));
        Assert.assertEquals(loginResult.tokenRefreshUrl(), new URI("https://api.product.com/token-refresh").toURL());
    }

    @Test
    public void givenAnnotatedLoginResult_whenSameApiToken_thenEqualInstances()
    throws Exception {
        String theSameApiToken = "testapitoken";

        LoginResult loginResult1 = new LoginResult(
                Instant.now(),
                theSameApiToken,
                Duration.ofHours(1),
                new URI("https://api.product.com/token-refresh").toURL());

        LoginResult loginResult2 = new LoginResult(
                Instant.now(),
                theSameApiToken,
                Duration.ofHours(2),
                new URI("https://api.product.com/token-refresh-alt").toURL());

        Assert.assertEquals(loginResult1, loginResult2);
    }

}
