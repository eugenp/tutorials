package com.baeldung.junit5.conditional;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ConditionalAnnotationsUnitTest {
    @Test
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    public void shouldRunBothWindowsAndMac() {
        //runs on Windows and Mac
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    public void shouldNotRunAtLinux() {
        //will not run on Linux
    }

    @Test
    @EnabledOnJre({JRE.JAVA_10, JRE.JAVA_11})
    public void shouldOnlyRunOnJava10And11() {
        //runs with java 10 and 11
    }

    @Test
    @DisabledOnJre(JRE.OTHER)
    public void thisTestOnlyRunsWithUpToDateJREs() {
        //this test will only run on java8, 9, 10 and 11.
    }

    @Test
    @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    public void onlyIfVendorNameStartsWithOracle() {
        //runs only if vendor name starts with Oracle
    }

    @Test
    @DisabledIfSystemProperty(named = "file.separator", matches = "[/]")
    public void disabledIfFileSeperatorIsSlash() {
        //Will not run if file.sepeartor property is /
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GDMSESSION", matches = "ubuntu")
    public void onlyRunOnUbuntuServer() {
        //Only runs if GDMSESSION is ubuntu
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LC_TIME", matches = ".*UTF-8.")
    public void shouldNotRunWhenTimeIsNotUTF8() {
        //Will not run if environment variable LC_TIME is UTF-8
    }

    @Test
    @EnabledIf("'FR' == systemProperty.get('user.country')")
    public void onlyFrenchPeopleWillRunThisMethod() {
        //Will run only if user.country is FR
        assertEquals('FR', System.getProperty("user.country"));
    }

    @Test
    @DisabledIf("java.lang.System.getProperty('os.name').toLowerCase().contains('mac')")
    public void shouldNotRunOnMacOS() {
        //Will not run if our os.name is mac
        assertFalse(System.getProperty('os.name').toLowerCase().contains('mac'));
    }

    @Test
    @EnabledIf(value = {
            "load('nashorn:mozilla_compat.js')",
            "importPackage(java.time)",
            "",
            "var thisMonth = LocalDate.now().getMonth().name()",
            "var february = Month.FEBRUARY.name()",
            "thisMonth.equals(february)"
    },
            engine = "nashorn",
            reason = "Self-fulfilling: {result}")
    public void onlyRunsInFebruary() {
        //This test only runs in February
    }

    @Test
    @DisabledIf("systemEnvironment.get('XPC_SERVICE_NAME') != null " +
            "&& systemEnvironment.get('XPC_SERVICE_NAME').contains('intellij')")
    public void notValidForIntelliJ() {
        //this test will run if our ide is INTELLIJ.
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
        //This method will run with java9, 10, 11 and Linux or macOS.
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @DisabledIf("Math.random() >= 0.5")
    @interface CoinToss {
    }

    @RepeatedTest(2)
    @CoinToss
    public void gamble() {
        //This test run status is a gamble with %50 rate
    }
}
