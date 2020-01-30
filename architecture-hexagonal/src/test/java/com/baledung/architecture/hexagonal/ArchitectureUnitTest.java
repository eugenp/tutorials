package com.baledung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.BlogControllerAdapter;
import com.baeldung.architecture.hexagonal.adapter.BlogServiceAdapter;
import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.service.DefaultBlogService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArchitectureUnitTest {

    static BlogControllerAdapter adapter;

    @BeforeClass
    public static void beforeClass() {
        adapter = new BlogControllerAdapter(new DefaultBlogService(new BlogServiceAdapter()));
    }

    @Test
    public void testCreateAndFindBlog() {
        final Blog blog = new Blog(1L, "Test");
        adapter.createBlog(blog);

        final Blog found = adapter.findByName("Test");
        Assert.assertNotNull(found);
        Assert.assertEquals(blog.getId(), found.getId());
        Assert.assertEquals(blog.getName(), found.getName());
    }
}
