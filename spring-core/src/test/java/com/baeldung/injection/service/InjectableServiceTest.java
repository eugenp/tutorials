package com.baeldung.injection.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class InjectableServiceTest {
        @Test
        public void whenProvidingValue_ThenMessageIsBuild() {
                InjectableService injectableService = new InjectableService();
                String outputValue = injectableService.performComplexOperation("test value");
                assertEquals(outputValue,"Operation has been performed with: test value");
        }
}