package com.baeldung.deep_shallow_copy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * @author amitkumar
 */
class BlogTest {

    @Test
    public void whenObjectIsCopiedUsingReference_thenShallowCopyIsCreated() {
        Blog blog = new Blog("Effective Jave", "some content", new Author("Joshua", "Bloch"));
        Blog newBlog = blog;
        assertEquals(blog.getContent(), newBlog.getContent());
        assertEquals(blog.getTitle(), newBlog.getTitle());
        assertEquals(blog.getAuthor()
          .getFirstName(), newBlog.getAuthor()
          .getFirstName());
        assertEquals(blog.getAuthor()
          .getLastName(), newBlog.getAuthor()
          .getLastName());
        blog.setContent("new Content");
        assertEquals(blog.getContent(), newBlog.getContent());
        newBlog.setTitle("new Title");
        assertEquals(blog.getTitle(), newBlog.getTitle());
    }

    @Test
    public void whenObjectIsCopiedUsingCopyConstructor_thenDeepCopyIsCreated() {
        Blog blog = new Blog("Effective Jave", "some content", new Author("Joshua", "Bloch"));
        Blog newBlog = blog.createCopy();
        assertEquals(blog.getContent(), newBlog.getContent());
        assertEquals(blog.getTitle(), newBlog.getTitle());
        assertEquals(blog.getAuthor()
          .getFirstName(), newBlog.getAuthor()
          .getFirstName());
        assertEquals(blog.getAuthor()
          .getLastName(), newBlog.getAuthor()
          .getLastName());
        blog.setContent("new Content");
        assertNotEquals(blog.getContent(), newBlog.getContent());
        newBlog.setTitle("new Title");
        assertNotEquals(blog.getTitle(), newBlog.getTitle());
    }

    @Test
    public void whenObjectIsCopiedUsingDefaultOverriddenCloneMethod_thenShallowCopyIsCreated() throws CloneNotSupportedException {
        Blog blog = new Blog("Effective Jave", "some content", new Author("Joshua", "Bloch"));
        Blog newBlog = (Blog) blog.clone();
        assertEquals(blog.getContent(), newBlog.getContent());
        assertEquals(blog.getTitle(), newBlog.getTitle());
        assertEquals(blog.getAuthor()
          .getFirstName(), newBlog.getAuthor()
          .getFirstName());
        assertEquals(blog.getAuthor()
          .getLastName(), newBlog.getAuthor()
          .getLastName());
        blog.getAuthor()
          .setLastName("last name");
        assertNotEquals(blog.getAuthor()
          .getLastName(), newBlog.getAuthor()
          .getLastName());
    }

    @Test
    public void whenObjectIsCopiedUsingCustomOverriddenCloneMethod_thenDeepCopyIsCreated() {
        Blog blog = new Blog("Effective Jave", "some content", new Author("Joshua", "Bloch"));
        Blog newBlog = blog.createCopy();
        assertEquals(blog.getContent(), newBlog.getContent());
        assertEquals(blog.getTitle(), newBlog.getTitle());
        assertEquals(blog.getAuthor()
          .getFirstName(), newBlog.getAuthor()
          .getFirstName());
        assertEquals(blog.getAuthor()
          .getLastName(), newBlog.getAuthor()
          .getLastName());
        blog.setContent("new Content");
        assertNotEquals(blog.getContent(), newBlog.getContent());
        newBlog.setTitle("new Title");
        assertNotEquals(blog.getTitle(), newBlog.getTitle());
    }

}