package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        article1 = new Article("Deep Copy", "Methodologies to do a deep copy...");
        article2 = new Article("Shallow Copy", "Methodologies to do a shallow copy ...");
        originalWebsite = new Baeldung(Arrays.asList(article1, article2));
    }

    @Test
    public void testShallowCopyCloneable() throws CloneNotSupportedException {
        originalWebsite = new Baeldung(Arrays.asList(article1, article2));
        Baeldung shallowCopyWebsite = (Baeldung) originalWebsite.clone();
        assertNotSame(originalWebsite, shallowCopyWebsite, "Shallow copy should not be the same instance.");
        assertSame(originalWebsite.getArticles(), shallowCopyWebsite.getArticles(), "Articles list should be the same instance.");
    }

    @Test
    public void testCloneShallowCopy_modifyingArticles() throws CloneNotSupportedException {
        originalWebsite = new Baeldung(Arrays.asList(article1, article2));
        Baeldung deepCopyWebsite = (Baeldung) originalWebsite.clone();
        article2.setTitle("Modified Shallow Copy");
        assertNotSame(originalWebsite, deepCopyWebsite, "Shallow copy should not be the same instance.");
        assertSame(originalWebsite.getArticles()
            .get(1), deepCopyWebsite.getArticles()
            .get(1), "Modified Article");
    }

    @Test
    public void testDeepCopyConstructor() {
        Baeldung copy = new Baeldung(Arrays.asList(article1, article2));
        assertNotSame(originalWebsite, copy);
        assertNotSame(originalWebsite.getArticles(), copy.getArticles(), "Articles list should be the same instance.");
    }

    @Test
    public void testDeepCopyApacheCommons() {
        Baeldung deepCopy = SerializationUtils.clone(originalWebsite);
        assertNotSame(originalWebsite, deepCopy, "Deep copy (Apache Commons) should not be the same instance.");
        assertNotSame(originalWebsite.getArticles(), deepCopy.getArticles(), "Articles list should be different instances.");
        assertEquals(originalWebsite.getArticles()
            .size(), deepCopy.getArticles()
            .size(), "Articles list sizes should be the same.");
        for (int i = 0; i < originalWebsite.getArticles()
            .size(); i++) {
            assertNotSame(originalWebsite.getArticles()
                .get(i), deepCopy.getArticles()
                .get(i), "Article instances should be different.");
        }
    }

    @Test
    public void testDeepCopyGson() {
        Gson gson = new Gson();
        Baeldung deepCopy = gson.fromJson(gson.toJson(originalWebsite), Baeldung.class);
        assertNotSame(originalWebsite, deepCopy, "Deep copy (Gson) should not be the same instance.");
        assertNotSame(originalWebsite.getArticles(), deepCopy.getArticles(), "Articles list should be different instances.");
        assertEquals(originalWebsite.getArticles()
            .size(), deepCopy.getArticles()
            .size(), "Articles list sizes should be the same.");
        for (int i = 0; i < originalWebsite.getArticles()
            .size(); i++) {
            assertNotSame(originalWebsite.getArticles()
                .get(i), deepCopy.getArticles()
                .get(i), "Article instances should be different.");
        }
    }

    @Test
    public void testDeepCopyJackson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Baeldung deepCopy = mapper.readValue(mapper.writeValueAsString(originalWebsite), Baeldung.class);
            assertNotSame(originalWebsite, deepCopy, "Deep copy (Jackson) should not be the same instance.");
            assertNotSame(originalWebsite.getArticles(), deepCopy.getArticles(), "Articles list should be different instances.");
            assertEquals(originalWebsite.getArticles()
                .size(), deepCopy.getArticles()
                .size(), "Articles list sizes should be the same.");
            for (int i = 0; i < originalWebsite.getArticles()
                .size(); i++) {
                assertNotSame(originalWebsite.getArticles()
                    .get(i), deepCopy.getArticles()
                    .get(i), "Article instances should be different.");
            }
        } catch (Exception e) {
            fail("Exception occurred during deep copy with Jackson: " + e.getMessage());
        }
    }
}



