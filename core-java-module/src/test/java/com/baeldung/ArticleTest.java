package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class ArticleTest {

    private Article originalArticle;

    @BeforeEach
    public void setUp() {
        originalArticle = new Article("Title 1", "Content 1");
    }

    @Test
    public void testShallowCopyCloneable() {
        Article copy = originalArticle.clone();
        assertNotSame(originalArticle, copy);
        assertEquals(originalArticle.getTitle(), copy.getTitle());
        assertEquals(originalArticle.getContent(), copy.getContent());
    }

    @Test
    public void testDeepCopyApacheCommons() {
        Article copy = SerializationUtils.clone(originalArticle);
        assertNotSame(originalArticle, copy);
        assertEquals(originalArticle.getTitle(), copy.getTitle());
        assertEquals(originalArticle.getContent(), copy.getContent());
    }

    @Test
    public void testDeepCopyGson() {
        Gson gson = new Gson();
        Article copy = gson.fromJson(gson.toJson(originalArticle), Article.class);
        assertNotSame(originalArticle, copy);
        assertEquals(originalArticle.getTitle(), copy.getTitle());
        assertEquals(originalArticle.getContent(), copy.getContent());
    }

    @Test
    public void testDeepCopyJackson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Article copy = mapper.readValue(mapper.writeValueAsString(originalArticle), Article.class);
            assertNotSame(originalArticle, copy);
            assertEquals(originalArticle.getTitle(), copy.getTitle());
            assertEquals(originalArticle.getContent(), copy.getContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

