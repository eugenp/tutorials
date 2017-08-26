package com.baeldung.di;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DiTest {
    @Test
    public void testDIUsage() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("di-example.xml");
        Bar bar = applicationContext.getBean("bar", Bar.class);
        assertThat(bar.getAge(), equalTo(26));
        assertThat(bar.getName(), equalTo("Arthur"));
        assertThat(bar.getFoo()
            .getName(), equalTo("Cleopatra"));

    }

}
