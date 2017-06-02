package org.baeldung.bean.injection.beanannotation;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanInjectionWithJavaConfigIntegrationTest {
    @Test
    public void givenAnnotationBeanForMethod_WhenUsingJavaConfigBeanInjection_ThenMembersAreSet() {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Author.class);
        ctx.register(Publisher.class);
        ctx.register(BookConfig.class);
        ctx.refresh();

        Book book = ctx.getBean(Book.class);
        Assert.assertNotNull(book.getAuthor());
        Assert.assertNotNull(book.getPublisher());

    }
}
