package com.baeldung.lombok.intro;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.baeldung.lombok.intro.ApiClientConfiguration.ApiClientConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ApiClientConfigurationIntegrationTest {

    @Test
    public void givenAnnotatedConfiguration_thenCanBeBuiltViaBuilder() {
        ApiClientConfiguration config =
            new ApiClientConfigurationBuilder()
                .host("api.server.com")
                .port(443)
                .useHttps(true)
                .connectTimeout(15_000L)
                .readTimeout(5_000L)
                .username("myusername")
                .password("secret")
            .build();

        Assert.assertEquals(config.getHost(), "api.server.com");
        Assert.assertEquals(config.getPort(), 443);
        Assert.assertEquals(config.isUseHttps(), true);
        Assert.assertEquals(config.getConnectTimeout(), 15_000L);
        Assert.assertEquals(config.getReadTimeout(), 5_000L);
        Assert.assertEquals(config.getUsername(), "myusername");
        Assert.assertEquals(config.getPassword(), "secret");
    }

    @Test
    public void givenAnnotatedConfiguration_thenHasLoggerInstance() throws NoSuchFieldException {
        Field loggerInstance = ApiClientConfiguration.class.getDeclaredField("log");
        int modifiers = loggerInstance.getModifiers();
        Assert.assertTrue(Modifier.isPrivate(modifiers));
        Assert.assertTrue(Modifier.isStatic(modifiers));
        Assert.assertTrue(Modifier.isFinal(modifiers));
    }

}
