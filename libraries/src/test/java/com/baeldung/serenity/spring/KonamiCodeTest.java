package com.baeldung.serenity.spring;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.serenitybdd.jbehave.SerenityStory;
import org.jbehave.core.annotations.BeforeStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author aiet
 */
@ContextConfiguration(classes = { KonamiCodeServiceInjectionController.class, KonamiCodeService.class })
public class KonamiCodeTest extends SerenityStory {

    @Autowired private KonamiCodeService konamiCodeService;

    @BeforeStory
    public void init() {
        RestAssuredMockMvc.standaloneSetup(new KonamiCodeServiceInjectionController(konamiCodeService));
    }

}
