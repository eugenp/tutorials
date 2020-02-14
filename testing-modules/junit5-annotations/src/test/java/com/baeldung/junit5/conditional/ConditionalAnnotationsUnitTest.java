package com.baeldung.junit5.conditional;

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
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_13)
    public void shouldOnlyRunOnJava8UntilJava13() {
        System.out.println("runs with Java 8, 9, 10, 11, 12 and 13!");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_14, max = JRE.JAVA_15)
    public void shouldNotBeRunOnJava14AndJava15() {
        System.out.println("Shouldn't be run on Java 14 and 15.");
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
}
