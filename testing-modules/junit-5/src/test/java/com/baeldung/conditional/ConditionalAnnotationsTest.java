package com.baeldung.conditional;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ConditionalAnnotationsTest {
    @Test
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    public void shouldRunBothWindowsAndMac() {
        //...
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    public void shouldNotRunAtLinux() {
        //...
    }

    @Test
    @EnabledOnJre({JRE.JAVA_10, JRE.JAVA_11})
    public void shouldOnlyRunOnJava10And11() {
        //...
    }

    @Test
    @DisabledOnJre(JRE.OTHER)
    public void thisTestOnlyRunsWithUpToDateJREs() {
        // this test will only run on java8, 9, 10 and 11.
    }

    @Test
    @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    public void onlyIfVendorNameStartsWithOracle() {
        //...
    }

    @Test
    @DisabledIfSystemProperty(named = "file.separator", matches = "[/]")
    public void disabledIfFileSeperatorIsSlash() {
        //...
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GDMSESSION", matches = "ubuntu")
    public void onlyRunOnUbuntuServer() {
        //...
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LC_TIME", matches = ".*UTF-8.")
    public void shouldNotRunWhenTimeIsNotUTF8() {
        //...
    }

    @Test
    @EnabledIf("'FR' == systemProperty.get('user.country')")
    public void onlyFrenchPeopleWillRunThisMethod() {
        //...
    }

    @Test
    @DisabledIf("java.lang.System.getProperty('os.name').toLowerCase().contains('mac')")
    public void shouldNotRunOnMacOS() {
        //...
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
    public void thisTestsOnlyRunsAtFebruary() {
        //...
    }

    @Test
    @DisabledIf("systemEnvironment.get('XPC_SERVICE_NAME') != null " +
            "&& systemEnvironment.get('XPC_SERVICE_NAME').contains('intellij')")
    public void notValidForIntelliJ() {
        //this method will not run on intelliJ
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
        //this method will run with java9, 10, 11 and Linux or macOS.
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @DisabledIf("Math.random() >= 0.5")
    @interface CoinToss {
    }

    @RepeatedTest(2)
    @CoinToss
    public void gamble() {
        System.out.println("This tests run status is a gamble with %50 rate");
    }
}
