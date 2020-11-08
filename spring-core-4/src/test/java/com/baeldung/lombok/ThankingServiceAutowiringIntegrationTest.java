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
public class ThankingServiceAutowiringIntegrationTest {

    @Autowired
    private ThankingService thankingService;

    @Autowired
    private Translator translator;

    @Test
    public void thankWithTranslatedMessage() {
        String translated = "translated";
        when(translator.translate("thank you")).thenReturn(translated);
        assertEquals(translated, thankingService.thank());
    }
}