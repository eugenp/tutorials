package com.baeldung.reflectionbeans;

import org.junit.Test;

import java.beans.PropertyDescriptor;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyDescriptorUnitTest {

    @Test
    public void givenPost_whenUsingPropertyDescriptor_thenReadAndWrite() throws Exception {
        Post post = new Post();
        PropertyDescriptor pd = new PropertyDescriptor("author", Post.class);

        pd.getWriteMethod().invoke(post, "Chris");
        assertEquals("Chris", pd.getReadMethod().invoke(post));
    }
}