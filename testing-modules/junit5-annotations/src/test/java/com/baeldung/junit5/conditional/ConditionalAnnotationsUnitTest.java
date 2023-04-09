package com.baeldung.junit5.conditional;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ConditionalAnnotationsUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalAnnotationsUnitTest.class);
    
    @Test
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    public void shouldRunBothWindowsAndMac() {
        LOGGER.debug("runs on Windows and Mac");
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    public void shouldNotRunAtLinux() {
        LOGGER.debug("will not run on Linux");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_10, JRE.JAVA_11})
    public void shouldOnlyRunOnJava10And11() {
        LOGGER.debug("runs with java 10 and 11");
    }

    @Test
    @EnabledInNativeImage
    void shouldOnlyRunWithinNativeImage() {
        LOGGER.debug("Should run only within native images.");
    }

    @Test
    @DisabledInNativeImage
    void shouldNeverRunWithinNativeImage() {
        LOGGER.debug("Shouldn't run within native images.");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_13)
    public void shouldOnlyRunOnJava8UntilJava13() {
        LOGGER.debug("runs with Java 8, 9, 10, 11, 12 and 13!");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_14, max = JRE.JAVA_15)
    public void shouldNotBeRunOnJava14AndJava15() {
        LOGGER.debug("Shouldn't be run on Java 14 and 15.");
    }

    @Test
    @DisabledOnJre(JRE.OTHER)
    public void thisTestOnlyRunsWithUpToDateJREs() {
        LOGGER.debug("this test will only run on java8, 9, 10 and 11.");
    }

    @Test
    @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    public void onlyIfVendorNameStartsWithOracle() {
        LOGGER.debug("runs only if vendor name starts with Oracle");
    }

    @Test
    @DisabledIfSystemProperty(named = "file.separator", matches = "[/]")
    public void disabledIfFileSeperatorIsSlash() {
        LOGGER.debug("Will not run if file.sepeartor property is /");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GDMSESSION", matches = "ubuntu")
    public void onlyRunOnUbuntuServer() {
        LOGGER.debug("only runs if GDMSESSION is ubuntu");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LC_TIME", matches = ".*UTF-8.")
    public void shouldNotRunWhenTimeIsNotUTF8() {
        LOGGER.debug("will not run if environment variable LC_TIME is UTF-8");
    }

    // Commented codes are going to work prior JUnit 5.5

    @Test
    // @EnabledIf("'FR' == systemProperty.get('user.country')")
    public void onlyFrenchPeopleWillRunThisMethod() {
        LOGGER.debug("will run only if user.country is FR");
    }

    @Test
    // @DisabledIf("java.lang.System.getProperty('os.name').toLowerCase().contains('mac')")
    public void shouldNotRunOnMacOS() {
        LOGGER.debug("will not run if our os.name is mac");
    }

    @Test
    /*@EnabledIf(value = {
      "load('nashorn:mozilla_compat.js')",
      "importPackage(java.time)",
      "",
      "var thisMonth = LocalDate.now().getMonth().name()",
      "var february = Month.FEBRUARY.name()",
      "thisMonth.equals(february)"
    },
      engine = "nashorn",
      reason = "Self-fulfilling: {result}")*/
    public void onlyRunsInFebruary() {
        LOGGER.debug("this test only runs in February");
    }

    @Test
    /*@DisabledIf("systemEnvironment.get('XPC_SERVICE_NAME') != null " +
      "&& systemEnvironment.get('XPC_SERVICE_NAME').contains('intellij')")*/
    public void notValidForIntelliJ() {
        LOGGER.debug("this test will run if our ide is INTELLIJ");
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @DisabledOnOs({OS.WINDOWS, OS.SOLARIS, OS.OTHER})
    @EnabledOnJre({JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    @interface ThisTestWillOnlyRunAtLinuxAndMacWithJava9Or10Or11 {
    }

    @ThisTestWillOnlyRunAtLinuxAndMacWithJava9Or10Or11
    public void someSuperTestMethodHere() {
        LOGGER.debug("this method will run with java9, 10, 11 and Linux or macOS.");
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    // @DisabledIf("Math.random() >= 0.5")
    @interface CoinToss {
    }

    @RepeatedTest(2)
    @CoinToss
    public void gamble() {
        LOGGER.debug("This tests run status is a gamble with %50 rate");
    }
}
