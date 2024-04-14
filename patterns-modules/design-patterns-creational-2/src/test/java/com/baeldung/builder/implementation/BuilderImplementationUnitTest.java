package com.baeldung.builder.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BuilderImplementationUnitTest {

    @Test
    void givenClassicBuilder_whenBuild_thenReturnObject() {

        Post post = new Post.Builder()
          .title("Java Builder Pattern")
          .text("Explaining how to implement the Builder Pattern in Java")
          .category("Programming")
          .build();

        assertEquals("Java Builder Pattern", post.getTitle());
        assertEquals("Explaining how to implement the Builder Pattern in Java", post.getText());
        assertEquals("Programming", post.getCategory());
    }

    @Test
    void givenGenericBuilder_whenBuild_thenReturnObject() {

        Post post = GenericBuilder.of(Post::new)
          .with(Post::setTitle, "Java Builder Pattern")
          .with(Post::setText, "Explaining how to implement the Builder Pattern in Java")
          .with(Post::setCategory, "Programming")
          .build();

        assertEquals("Java Builder Pattern", post.getTitle());
        assertEquals("Explaining how to implement the Builder Pattern in Java", post.getText());
        assertEquals("Programming", post.getCategory());
    }

    @Test
    void givenLombokBuilder_whenBuild_thenReturnObject() {

        LombokPost post = LombokPost.builder()
          .title("Java Builder Pattern")
          .text("Explaining how to implement the Builder Pattern in Java")
          .category("Programming")
          .build();

        assertEquals("Java Builder Pattern", post.getTitle());
        assertEquals("Explaining how to implement the Builder Pattern in Java", post.getText());
        assertEquals("Programming", post.getCategory());
    }
}
