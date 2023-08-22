package com.baeldung.deepvsshallowcopy;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BlogPostUnitTest {

    @Test
    public void whenCreatingShallowCopy_thenReferencesToFieldObjectsAreCopied() {
        BlogPost blogPost = new BlogPost("Deep vs Shallow Copy", "In this article...", new Author("Constantin", "Ursache"), singletonList("Java"));
        BlogPost blogPostCopy = new BlogPost(blogPost.getTitle(), blogPost.getContent(), blogPost.getAuthor(), blogPost.getTags());

        assertThat(blogPost).isNotSameAs(blogPostCopy);
        assertThat(blogPost.getTitle()).isSameAs(blogPostCopy.getTitle());
        assertThat(blogPost.getContent()).isSameAs(blogPostCopy.getContent());
        assertThat(blogPost.getTags()).isSameAs(blogPostCopy.getTags());
        assertThat(blogPost.getAuthor()).isSameAs(blogPostCopy.getAuthor());
    }

    @Test
    public void whenCreatingDeepCopy_thenFieldObjectsAreRecursivelyCopied() {
        BlogPost blogPost = new BlogPost("Deep vs Shallow Copy", "In this article...", new Author("Constantin", "Ursache"), singletonList("Java"));
        BlogPost blogPostCopy = new BlogPost(blogPost);

        assertThat(blogPost).isNotSameAs(blogPostCopy);
        assertThat(blogPost.getTitle()).isSameAs(blogPostCopy.getTitle());
        assertThat(blogPost.getContent()).isSameAs(blogPostCopy.getContent());
        assertThat(blogPost.getAuthor()).isNotSameAs(blogPostCopy.getAuthor());
        assertThat(blogPost.getTags()).isNotSameAs(blogPostCopy.getTags());
    }
}