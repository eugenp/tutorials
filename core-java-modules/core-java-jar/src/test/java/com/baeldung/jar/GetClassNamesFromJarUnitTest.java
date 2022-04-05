package com.baeldung.jar;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GetClassNamesFromJarUnitTest {
    private static final String JAR_PATH = "example-jar/stripe-0.0.1-SNAPSHOT.jar";
    private static final Set<String> EXPECTED_CLASS_NAMES = Sets.newHashSet(
      "com.baeldung.stripe.StripeApplication",
      "com.baeldung.stripe.ChargeRequest",
      "com.baeldung.stripe.StripeService",
      "com.baeldung.stripe.ChargeRequest$Currency",
      "com.baeldung.stripe.ChargeController",
      "com.baeldung.stripe.CheckoutController");

    @Test
    public void givenJarFilePath_whenLoadClassNames_thenGetClassNames() throws IOException, URISyntaxException {
        File jarFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(JAR_PATH)).toURI());
        Set<String> classNames = GetClassNamesFromJar.getClassNamesFromJarFile(jarFile);
        Assert.assertEquals(EXPECTED_CLASS_NAMES, classNames);
    }

    @Test
    public void givenJarFilePath_whenLoadClass_thenGetClassObjects() throws IOException, ClassNotFoundException, URISyntaxException {
        File jarFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(JAR_PATH)).toURI());
        Set<Class> classes = GetClassNamesFromJar.getClassesFromJarFile(jarFile);
        Set<String> names = classes.stream().map(Class::getName).collect(Collectors.toSet());
        Assert.assertEquals(EXPECTED_CLASS_NAMES, names);
    }
}
