package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class BaeldungTest {

    private Baeldung originalWebsite;
    private Article article1;
    private Article article2;

    @BeforeEach
    public void setUp() {
        article1 = new Article("Deep Copy", new StringBuilder("Methodologies to do a deep copy..."));
        article2 = new Article("Shallow Copy", new StringBuilder("Methodologies to do a shallow copy ..."));
        originalWebsite = new Baeldung(Arrays.asList(article1, article2));
    }

    @Test
    public void testShallowCopyCloneable() {
        Baeldung shallowCopyWebsite = (Baeldung) originalWebsite.clone();
        assertNotSame(originalWebsite, shallowCopyWebsite, "Shallow copy should not be the same instance.");
        assertSame(originalWebsite.getArticles(), shallowCopyWebsite.getArticles(), "Articles list should be the same instance.");
    }

    @Test
    public void testCloneShallowCopy_modifyingArticles() {
        Baeldung copyWebsite = (Baeldung) originalWebsite.clone();
        copyWebsite.getArticles()
            .forEach(s -> s.getContent()
                .append(" Some additional content.."));
        assertNotSame(originalWebsite, copyWebsite, "Shallow copy should not be the same instance.");
        assertSame(originalWebsite.getArticles(), copyWebsite.getArticles(), "Object reference is copied.");
        for (int i = 0; i < originalWebsite.getArticles()
            .size(); i++) {
            assertSame(originalWebsite.getArticles()
                .get(i)
                .getContent(), copyWebsite.getArticles()
                .get(i)
                .getContent(), "Modifying the content effects all the related objects");
        }
    }

    @Test
    public void testDeepCopyConstructor() {
        Baeldung copyWebsite = new Baeldung(originalWebsite);
        copyWebsite.getArticles()
            .forEach(s -> s.getContent()
                .append(" Some additional content.."));
        assertNotSame(originalWebsite, copyWebsite);
        assertNotSame(originalWebsite.getArticles(), copyWebsite.getArticles(), "Articles list should be different instances.");
        for (int i = 0; i < originalWebsite.getArticles()
            .size(); i++) {
            assertNotSame(originalWebsite.getArticles()
                .get(i)
                .getContent(), copyWebsite.getArticles()
                .get(i)
                .getContent(), "Article instances should be different.");
        }
    }

@Test
public void testDeepCopyApacheCommons() {
    Baeldung copyWebsite = SerializationUtils.clone(originalWebsite);
    copyWebsite.getArticles()
        .forEach(s -> s.getContent()
            .append(" Some additional content.."));
    assertNotSame(originalWebsite, copyWebsite, "Deep copy (Apache Commons) should not be the same instance.");
    assertNotSame(originalWebsite.getArticles(), copyWebsite.getArticles(), "Articles list should be different instances.");
    for (int i = 0; i < originalWebsite.getArticles()
        .size(); i++) {
        assertNotSame(originalWebsite.getArticles()
            .get(i), copyWebsite.getArticles()
            .get(i), "Article instances should be different.");
    }
}

    @Test
    public void testDeepCopyGson() {
        Gson gson = new Gson();
        Baeldung copyWebsite = gson.fromJson(gson.toJson(originalWebsite), Baeldung.class);
        copyWebsite.getArticles()
            .forEach(s -> s.getContent()
                .append(" Some additional content.."));
        assertNotSame(originalWebsite, copyWebsite, "Deep copy (Gson) should not be the same instance.");
        assertNotSame(originalWebsite.getArticles(), copyWebsite.getArticles(), "Articles list should be different instances.");
        for (int i = 0; i < originalWebsite.getArticles()
            .size(); i++) {
            assertNotSame(originalWebsite.getArticles()
                .get(i), copyWebsite.getArticles()
                .get(i), "Article instances should be different.");
        }
    }

    @Test
    public void testDeepCopyJackson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Baeldung copyWebsite = mapper.readValue(mapper.writeValueAsString(originalWebsite), Baeldung.class);
            copyWebsite.getArticles()
                .forEach(s -> s.getContent()
                    .append(" Some additional content.."));
            assertNotSame(originalWebsite, copyWebsite, "Deep copy (Jackson) should not be the same instance.");
            assertNotSame(originalWebsite.getArticles(), copyWebsite.getArticles(), "Articles list should be different instances.");
            for (int i = 0; i < originalWebsite.getArticles()
                .size(); i++) {
                assertNotSame(originalWebsite.getArticles()
                    .get(i), copyWebsite.getArticles()
                    .get(i), "Article instances should be different.");
            }
        } catch (Exception e) {
            fail("Exception occurred during deep copy with Jackson: " + e.getMessage());
        }
    }
}



