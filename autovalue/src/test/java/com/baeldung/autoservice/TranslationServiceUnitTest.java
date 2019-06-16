package com.baeldung.autoservice;

import com.baeldung.autoservice.TranslationService;
import org.junit.Test;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class TranslationServiceUnitTest {

    @Test
    public void whenServiceLoaderLoads_thenLoadsAllProviders() {

      ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);
      long count = StreamSupport.stream(loader.spliterator(), false).count();
      assertEquals(2, count);
    }

    @Test
    public void whenServiceLoaderLoadsGoogleService_thenGoogleIsLoaded() {

      ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);

      TranslationService googleService = StreamSupport.stream(loader.spliterator(), false)
        .filter(p -> p.getClass().getSimpleName().equals("GoogleTranslationServiceProvider"))
        .findFirst()
        .get();

      assertEquals("translated by Google", googleService.translate("message", null, null));

    }
}