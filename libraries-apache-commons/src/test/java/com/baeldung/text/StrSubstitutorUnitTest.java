package com.baeldung.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StrSubstitutor;
import org.junit.Assert;
import org.junit.Test;

public class StrSubstitutorUnitTest {

    @Test
    public void whenSubstituted_thenCorrect() {
        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("name", "John");
        substitutes.put("college", "University of Stanford");
        String templateString = "My name is ${name} and I am a student at the ${college}.";
        StrSubstitutor sub = new StrSubstitutor(substitutes);
        String result = sub.replace(templateString);

        Assert.assertEquals("My name is John and I am a student at the University of Stanford.", result);
    }
}
