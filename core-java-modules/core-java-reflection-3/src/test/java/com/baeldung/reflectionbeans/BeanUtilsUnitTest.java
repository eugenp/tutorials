package com.baeldung.reflectionbeans;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BeanUtilsUnitTest {

    @Test
    public void givenPost_whenPopulate_thenFieldsAreSet() throws Exception {
        Post post = new Post();
        Map<String, Object> data = Map.of("title", "Populate Test", "author", "Dana");

        BeanUtils.populate(post, data);

        assertEquals("Populate Test", BeanUtils.getProperty(post, "title"));
        assertEquals("Dana", BeanUtils.getProperty(post, "author"));
    }

    @Test
    public void givenTwoPosts_whenCopyProperties_thenTargetMatchesSource() throws Exception {
        Post source = new Post();
        source.setTitle("Copy");
        source.setAuthor("Eve");

        Post target = new Post();
        BeanUtils.copyProperties(target, source);

        assertEquals("Copy", target.getTitle());
        assertEquals("Eve", target.getAuthor());
    }
}

