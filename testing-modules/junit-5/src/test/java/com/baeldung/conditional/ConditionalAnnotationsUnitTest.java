package com.baeldung.conditional;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ConditionalAnnotationsUnitTest {
    @Test
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    public void shouldRunBothWindowsAndMac() {
        System.out.println("runs on Windows and Mac");
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    public void shouldNotRunAtLinux() {
        System.out.println("will not run on Linux");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_10, JRE.JAVA_11})
    public void shouldOnlyRunOnJava10And11() {
        System.out.println("runs with java 10 and 11");
    }

    @Test
    @DisabledOnJre(JRE.OTHER)
    public void thisTestOnlyRunsWithUpToDateJREs() {
        System.out.println("this test will only run on java8, 9, 10 and 11.");
    }

    @Test
    @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    public void onlyIfVendorNameStartsWithOracle() {
        System.out.println("runs only if vendor name starts with Oracle");
    }

    @Test
    @DisabledIfSystemProperty(named = "file.separator", matches = "[/]")
    public void disabledIfFileSeperatorIsSlash() {
        System.out.println("Will not run if file.sepeartor property is /");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GDMSESSION", matches = "ubuntu")
    public void onlyRunOnUbuntuServer() {
        System.out.println("only runs if GDMSESSION is ubuntu");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LC_TIME", matches = ".*UTF-8.")
    public void shouldNotRunWhenTimeIsNotUTF8() {
        System.out.println("will not run if environment variable LC_TIME is UTF-8");
    }

    @Test
    @EnabledIf("'FR' == systemProperty.get('user.country')")
    public void onlyFrenchPeopleWillRunThisMethod() {
        System.out.println("will run only if user.country is FR");
    }

    @Test
    @DisabledIf("java.lang.System.getProperty('os.name').toLowerCase().contains('mac')")
    public void shouldNotRunOnMacOS() {
        System.out.println("will not run if our os.name is mac");
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
        System.out.println("this test only runs in February");
    }

    @Test
    @DisabledIf("systemEnvironment.get('XPC_SERVICE_NAME') != null " +
            "&& systemEnvironment.get('XPC_SERVICE_NAME').contains('intellij')")
    public void notValidForIntelliJ() {
        System.out.println("this test will run if our ide is INTELLIJ");
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
        System.out.println("this method will run with java9, 10, 11 and Linux or macOS.");
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
