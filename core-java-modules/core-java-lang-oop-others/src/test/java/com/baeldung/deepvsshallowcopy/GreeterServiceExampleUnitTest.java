package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;

public class GreeterServiceExampleUnitTest {

    @Test
    public void whenModifyingAShallowCopy_itShouldAffectTheOriginal() {
        GreeterServiceExample greeterService
            = new GreeterServiceExample();
        Greeting englishGreeting
            = greeterService.greetInYourLanguage("English");
        assertEquals(
            "en", englishGreeting.getLanguage().getCode());

        Greeting xhosaGreeting = englishGreeting.clone();
        xhosaGreeting.getLanguage().setName("Xhosa");
        xhosaGreeting.getLanguage().setCode("xh");
        // assert that the original Object has been affected by the copied change
        assertNotEquals(
            "en", englishGreeting.getLanguage().getCode());
    }

    @Test
    public void editingAnObjectUtilsClone_shouldAlsoAffectTheOriginal() {
        GreeterServiceExample greeterService
            = new GreeterServiceExample();
        Greeting englishGreeting
            = greeterService.greetInYourLanguage("English");
        assertEquals(
            "en", englishGreeting.getLanguage().getCode());

        Greeting spanishGreeting = ObjectUtils.clone(englishGreeting);
        spanishGreeting.getLanguage().setName("Spanish");
        spanishGreeting.getLanguage().setCode("es");
        // assert that the original Object has been affected by the copied Object change
        assertNotEquals("en", englishGreeting.getLanguage().getCode());
    }

    @Test
    public void modifyingADeepCopy_shouldNotAffectTheOriginal() {
        GreeterServiceExample greeterService
            = new GreeterServiceExample();
        Greeting englishGreeting
            = greeterService.greetInYourLanguage("English");
        assertEquals(
            "en", englishGreeting.getLanguage().getCode());

        Greeting xhosaGreeting
            = SerializationUtils.clone(englishGreeting);
        xhosaGreeting.getLanguage().setName("Xhosa");
        xhosaGreeting.getLanguage().setCode("xh");
        // assert that the original Object has NOT been affected by the copied Object update
        assertEquals(
            "en", englishGreeting.getLanguage().getCode());
    }

}
