package com.baeldung.lombok.intro;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

public class LoginResultLiveTest {

    @Test
    public void givenAnnotatedLoginResult_thenHasConstructorForAllFinalFields()
    throws MalformedURLException {
        /* LoginResult loginResult = */ new LoginResult(
                Instant.now(),
                "apitoken",
                Duration.ofHours(1),
                new URL("https://api.product.com/token-refresh"));
    }

    @Test
    public void givenAnnotatedLoginResult_thenHasFluentGetters()
    throws MalformedURLException {
        Instant loginTs = Instant.now();
        LoginResult loginResult = new LoginResult(
                loginTs,
                "apitoken",
                Duration.ofHours(1),
                new URL("https://api.product.com/token-refresh"));

        Assert.assertEquals(loginResult.loginTs(), loginTs);
        Assert.assertEquals(loginResult.authToken(), "apitoken");
        Assert.assertEquals(loginResult.tokenValidity(), Duration.ofHours(1));
        Assert.assertEquals(loginResult.tokenRefreshUrl(), new URL("https://api.product.com/token-refresh"));
    }

    @Test
    public void givenAnnotatedLoginResult_whenSameApiToken_thenEqualInstances()
    throws MalformedURLException {
        String theSameApiToken = "testapitoken";

        LoginResult loginResult1 = new LoginResult(
                Instant.now(),
                theSameApiToken,
                Duration.ofHours(1),
                new URL("https://api.product.com/token-refresh"));

        LoginResult loginResult2 = new LoginResult(
                Instant.now(),
                theSameApiToken,
                Duration.ofHours(2),
                new URL("https://api.product.com/token-refresh-alt"));

        Assert.assertEquals(loginResult1, loginResult2);
    }

}
