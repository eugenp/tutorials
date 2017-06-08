package com.baeldung.lombok;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = TestConfig.class)
public class GreetingServiceIntegrationTest {

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private Translator translator;

    @Test
    public void greetWithTranslatedMessage() {
        String translated = "translated";
        when(translator.translate("hello")).thenReturn(translated);
        assertEquals(translated, greetingService.greet());
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenInstantiated() {
        GreetingService greetingService = new GreetingService();
        greetingService.greet();
    }
}