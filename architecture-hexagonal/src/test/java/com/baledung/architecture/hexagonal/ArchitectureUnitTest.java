package com.baledung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.BlogControllerAdapter;
import com.baeldung.architecture.hexagonal.adapter.BlogServiceAdapter;
import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.port.BlogUIPort;
import com.baeldung.architecture.hexagonal.service.DefaultBlogService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArchitectureUnitTest {

    static BlogUIPort blogUIPort;

    @BeforeClass
    public static void beforeClass() {
        blogUIPort = new BlogControllerAdapter(new DefaultBlogService(new BlogServiceAdapter()));
    }

    @Test
    public void testCreateAndFindBlog() {
        final Blog blog = new Blog(1L, "Test");
        blogUIPort.createBlog(blog);

        final Blog found = blogUIPort.findByName("Test");
        Assert.assertNotNull(found);
        Assert.assertEquals(blog.getId(), found.getId());
        Assert.assertEquals(blog.getName(), found.getName());
    }
}
