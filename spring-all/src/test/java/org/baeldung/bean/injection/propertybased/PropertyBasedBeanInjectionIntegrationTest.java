package org.baeldung.bean.injection.propertybased;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PropertyBasedBeanInjectionIntegrationTest {
    @Test
    public void givenAnnotationForProperties_WhenUsingPropertyBasedBeanInjection_ThenAutowiredPropertiesAreSet() {
       
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Author.class);
        ctx.register(Publisher.class);
        ctx.register(Book.class);
        ctx.refresh();
        
        Book book = ctx.getBean(Book.class);
        Assert.assertNotNull(book.getAuthor());
        Assert.assertNotNull(book.getPublisher());
        
    }
}
