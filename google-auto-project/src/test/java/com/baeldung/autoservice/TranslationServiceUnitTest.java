package com.baeldung.autoservice;

import com.baeldung.autoservice.TranslationService;
import org.junit.Before;
import org.junit.Test;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class TranslationServiceUnitTest {

    private ServiceLoader<TranslationService> loader;

    @Before
    public void setUp() {
        loader = ServiceLoader.load(TranslationService.class);
    }

    @Test
    public void whenServiceLoaderLoads_thenLoadsAllProviders() {
        long count = StreamSupport.stream(loader.spliterator(), false).count();
        assertEquals(2, count);
    }

    @Test
    public void whenServiceLoaderLoadsGoogleService_thenGoogleIsLoaded() {
        TranslationService googleService = StreamSupport.stream(loader.spliterator(), false)
          .filter(p -> p.getClass().getSimpleName().equals("GoogleTranslationServiceProvider"))
          .findFirst()
          .get();
		
        String message = "message";
        assertEquals(message + " (translated by Google)", googleService.translate(message, null, null));
    }
}