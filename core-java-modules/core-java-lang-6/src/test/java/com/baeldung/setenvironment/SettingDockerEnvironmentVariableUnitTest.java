package com.baeldung.setenvironment;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class SettingDockerEnvironmentVariableUnitTest {

   @Test
   void givenDockerEnvironment_whenGetEnvironmentVariable_thenReturnsCorrectValue() {
       String expected = "TRUE";
       String actual = System.getenv("CUSTOM_DOCKER_ENV_VARIABLE");
       assertEquals(actual,expected);
   }
}
